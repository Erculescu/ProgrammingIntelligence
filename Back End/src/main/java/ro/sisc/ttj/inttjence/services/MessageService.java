package ro.sisc.ttj.inttjence.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.springframework.stereotype.Service;
import ro.sisc.ttj.inttjence.dtos.MessageAnswerDto;
import ro.sisc.ttj.inttjence.dtos.MessageDto;
import ro.sisc.ttj.inttjence.dtos.MessageRequestDto;
import ro.sisc.ttj.inttjence.models.Message;
import ro.sisc.ttj.inttjence.models.Model;
import ro.sisc.ttj.inttjence.repository.MessageRepository;
import ro.sisc.ttj.inttjence.repository.ModelRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private MessageRepository messageRepository;
    private ModelRepository modelRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OkHttpClient httpClient = new OkHttpClient.Builder().build();

    public List<MessageDto> getAllMessages() {
        List<Message> messagesFromDb = messageRepository.findAllByOrderByInsertTimestampAsc();

        return messagesFromDb.stream()
                .map(m -> {
                    MessageDto dto = new MessageDto();
                    dto.setContent(m.getContent());
                    dto.setModel(m.getModel());
                    dto.setRole(m.getRole());
                    return dto;
                }).toList();
    }

    public MessageDto createMessage(MessageDto dto) {
        List<Message> previousMessages = messageRepository.findAll();
        previousMessages.add(createMessageFromDto(dto));

        List<MessageDto> contextMessages = previousMessages.stream()
                .map(message -> createMessageDtoFromMessage(message))
                .toList();

        Model targetModel = modelRepository.findFirstByName(dto.getModel());

        MessageRequestDto messageRequestDto = new MessageRequestDto();
        messageRequestDto.setModel(targetModel.getVersion());
        messageRequestDto.setMessages(contextMessages);

        String serializedRequest;
        try {
            serializedRequest = objectMapper.writeValueAsString(messageRequestDto);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Serialization failed");
        }

        Request request = new Request.Builder()
                .url(targetModel.getUrl())
                .post(RequestBody.create(serializedRequest, MediaType.get("application/json")))
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", " Bearer " + targetModel.getApiKey().trim())
                .build();

        try (Response httpResponse = httpClient.newCall(request).execute()) {
            if (!httpResponse.isSuccessful() || httpResponse.body() == null) {
                throw new RuntimeException("Connection to LLM failed");
            }

            MessageAnswerDto responseDto = objectMapper.readValue(httpResponse.body().byteStream(), MessageAnswerDto.class);

            if (null == responseDto.getChoices() || responseDto.getChoices().isEmpty()) {
                throw new RuntimeException("The LLM response is malformed");
            }

            MessageDto finalResponse = responseDto.getChoices().getFirst().getMessage();

            createMessageFromDto(finalResponse);

            return finalResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MessageDto createMessageDtoFromMessage(Message message) {
        MessageDto dto = new MessageDto();
        dto.setRole(message.getRole());
        dto.setContent(message.getContent());
        return dto;
    }

    private Message createMessageFromDto(MessageDto dto) {
        Message message = new Message();
        message.setRole(dto.getRole());
        message.setContent(dto.getContent());
        message.setModel(dto.getModel());
        message.setInsertTimestamp(LocalDateTime.now(ZoneId.of(("UTC"))));

        return messageRepository.save(message);
    }
}
