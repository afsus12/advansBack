package com.example.finalbackendadvans.services;

import com.example.finalbackendadvans.dto.MessageDTO;
import com.example.finalbackendadvans.entities.Client.Client;
import com.example.finalbackendadvans.entities.Message;
import com.example.finalbackendadvans.entities.Staff;
import com.example.finalbackendadvans.repositories.ClientRepository;
import com.example.finalbackendadvans.repositories.MessageRepository;
import com.example.finalbackendadvans.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ClientRepository clientRepository; // Assuming you have this repository
    @Autowired
    private StaffRepository staffRepository; // Assuming you have this repository

    public MessageDTO sendMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setTimestamp(new Date());

        Client client = clientRepository.findById(Long.parseLong(messageDTO.getFrom())).orElse(null);
        Staff staff = staffRepository.findById(Long.parseLong(messageDTO.getTo())).orElse(null);

        message.setClient(client);
        message.setStaff(staff);
        message.setFrom(client.getId());
        Message savedMessage = messageRepository.save(message);
        return new MessageDTO(savedMessage.getFrom().toString(), savedMessage.getStaff().getId().toString(), savedMessage.getContent());
    }
    public MessageDTO sendMessageStaff(MessageDTO messageDTO) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setTimestamp(new Date());

        Staff staff = staffRepository.findById(Long.parseLong(messageDTO.getFrom())).orElse(null);
       Client client = clientRepository.findById(Long.parseLong(messageDTO.getTo())).orElse(null);

        message.setClient(client);
        message.setStaff(staff);
       message.setFrom(staff.getId());
        Message savedMessage = messageRepository.save(message);
        return new MessageDTO(savedMessage.getStaff().getId().toString(),savedMessage.getClient().getId().toString(), savedMessage.getContent());
    }

    public List<MessageDTO> getMessagesByClient(Long clientId) {
       return messageRepository.findByClientId(clientId).stream()
                .map(msg -> new MessageDTO(msg.getFrom().toString(), msg.getStaff().getId().toString(), msg.getContent()))
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getMessagesByStaff(Long staffId) {
        return messageRepository.findByStaffId(staffId).stream()
                .map(msg -> new MessageDTO(msg.getFrom().toString(), msg.getClient().getId().toString(), msg.getContent()))
                .collect(Collectors.toList());
    }
}