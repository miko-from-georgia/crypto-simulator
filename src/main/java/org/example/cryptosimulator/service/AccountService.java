package org.example.cryptosimulator.service;

import org.example.cryptosimulator.model.Account;
import org.example.cryptosimulator.repo.AccountRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findByLogin(String login) {
        return accountRepository.findByAuthorizationLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + login));
    }
}

