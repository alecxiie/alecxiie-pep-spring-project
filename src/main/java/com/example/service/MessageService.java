package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message persistMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public void deleteMessageById(Integer id){
        messageRepository.deleteById(id);
    }

    public Message updateMessageById(Integer id, String messageText){
        if(messageText.length() == 0 || messageText.length() > 255){
            return null;
        }

        Message message = messageRepository.findMessageByMessageId(id);
        if(message != null){
            message.setMessageText(messageText);
            messageRepository.save(message);
        }
        
        return message;
    }

    public Message getMessageById(Integer id){
        return messageRepository.findMessageByMessageId(id);
    }

    public List<Message> getAllMessagesByAccountId(Integer accountId){
        return messageRepository.findMessagesByPostedBy(accountId);
    }

}
