package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<?> postAccountHandler(@RequestBody Account account){
        if(account.getPassword().length() < 4 || account.getUsername().length() == 0){
            return ResponseEntity.status(400).build();
        }

        if(null != accountService.getAccountByUsername(account.getUsername())){
            return ResponseEntity.status(409).build();
        }

        return ResponseEntity.ok(accountService.persistAccount(account));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginHandler(@RequestBody Account account){
        Account match = accountService.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(match == null){
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(match);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> postMessageHandler(@RequestBody Message message){
        int textLength = message.getMessageText().length();
        int posterId = message.getPostedBy();
        if(textLength == 0 || textLength > 255 || accountService.getAccountById(posterId) == null){
                return ResponseEntity.status(400).build();
        }

        return ResponseEntity.ok(messageService.persistMessage(message));
    }

    @GetMapping("/messages")
    public List<Message> getAllMessagesHandler(){
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{messageId}")
    public Message getMessageByIdHandler(@PathVariable Integer messageId){
        return messageService.getMessageById(messageId);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageByIdHandler(@PathVariable Integer messageId){
        if(messageService.getMessageById(messageId) == null){
            return ResponseEntity.ok().build();
        }

        messageService.deleteMessageById(messageId);
        return ResponseEntity.ok(1);
    }
    
    
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> patchMessageByIdHandler(@PathVariable Integer messageId, @RequestBody Message message){
        if(messageService.updateMessageById(messageId, message.getMessageText()) == null){
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.ok(1);
    }
    
    
    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getAllMessagesByAccountIdHandler(@PathVariable Integer accountId){
        return messageService.getAllMessagesByAccountId(accountId);
    }
}
