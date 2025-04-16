package ro.sisc.ttj.inttjence.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MessageRequestDto {
    private String model;
    private List<MessageDto> messages;
}
