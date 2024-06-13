package com.example.finalbackendadvans.services;

import com.example.finalbackendadvans.entities.Message;
import com.example.finalbackendadvans.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesByClient(Long clientId) {
        return messageRepository.findByClientId(clientId);
    }

    public List<Message> getMessagesByStaff(Long staffId) {
        return messageRepository.findByStaffId(staffId);
    }

    public Message sendMessage(Message message) {
        message.setTimestamp(new Date());
        return messageRepository.save(message);
    }
}