package org.example.cryptosimulator.service;

import org.example.cryptosimulator.model.Account;
import org.example.cryptosimulator.model.Transaction;
import org.example.cryptosimulator.repo.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findByAccount(Account account) {
        return transactionRepository.findByAccount(account);
    }
}

