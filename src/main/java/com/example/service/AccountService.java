package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountService {
    
    private AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account persistAccount(Account account){
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account getAccountById(Integer id){
        return accountRepository.findAccountByAccountId(id);
    }

    public Account getAccountByUsername(String username){
        return accountRepository.findAccountByUsername(username);
        
    }

    public Account getAccountByUsernameAndPassword(String username, String password){
        return accountRepository.findAccountByUsernameAndPassword(username, password);
    }

}
