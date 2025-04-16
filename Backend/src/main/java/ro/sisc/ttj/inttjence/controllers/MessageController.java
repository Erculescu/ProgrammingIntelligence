package ro.sisc.ttj.inttjence.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.sisc.ttj.inttjence.dtos.MessageDto;
import ro.sisc.ttj.inttjence.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
@CrossOrigin("*")
public class MessageController {
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageDto>> getMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @PostMapping
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDto) {
        return ResponseEntity.ok(messageService.createMessage(messageDto));
    }
}
