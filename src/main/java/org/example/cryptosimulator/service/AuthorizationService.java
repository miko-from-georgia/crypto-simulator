package org.example.cryptosimulator.service;

import org.example.cryptosimulator.dto.RegistrationDto;
import org.example.cryptosimulator.model.Account;
import org.example.cryptosimulator.model.Authorization;
import org.example.cryptosimulator.model.Wallet;
import org.example.cryptosimulator.repo.AccountRepository;
import org.example.cryptosimulator.repo.AuthorizationRepository;
import org.example.cryptosimulator.repo.WalletRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorizationService {

    private final AuthorizationRepository authRepository;

    private final AccountRepository accountRepository;

    private final WalletRepository walletRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthorizationService(AuthorizationRepository authRepository, AccountRepository accountRepository, WalletRepository walletRepository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.accountRepository = accountRepository;
        this.walletRepository = walletRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerNewClient(RegistrationDto dto) {
        if (authRepository.findByLogin(dto.getLogin()) != null) {
            throw new RuntimeException("Client already exists: " + dto.getLogin());
        }

        Authorization authorization = new Authorization();
        authorization.setLogin(dto.getLogin());
        authorization.setPassword(passwordEncoder.encode(dto.getPassword()));
        authorization.setEmail(dto.getEmail());
        authorization.setRole("ROLE_USER");

        authRepository.save(authorization);

        Wallet wallet = new Wallet();
        walletRepository.save(wallet);

        Account account = new Account();
        account.setAuthorization(authorization);
        account.setWallet(wallet);
        accountRepository.save(account);
    }
}

