package com.example.finalbackendadvans.controllers;

import com.example.finalbackendadvans.dto.MessageDTO;
import com.example.finalbackendadvans.entities.Message;
import com.example.finalbackendadvans.repositories.MessageRepository;
import com.example.finalbackendadvans.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @SendTo("/topic/messages")
    public MessageDTO send(MessageDTO messageDTO) throws Exception {
        return messageService.sendMessage(messageDTO);
    }

    @GetMapping("/client/{clientId}")
    public List<MessageDTO> getMessagesByClient(@PathVariable Long clientId) {
        return messageService.getMessagesByClient(clientId);
    }

    @GetMapping("/staff/{staffId}")
    public List<MessageDTO> getMessagesByStaff(@PathVariable Long staffId) {
        return messageService.getMessagesByStaff(staffId);
    }
    @PostMapping("/staff/send")
    public MessageDTO sendMessageFromStaff(@RequestBody MessageDTO messageDTO) {
        return messageService.sendMessageStaff(messageDTO);
    }
    @PostMapping
    public MessageDTO sendMessage(@RequestBody MessageDTO messageDTO) {
        return messageService.sendMessage(messageDTO);
    }
}