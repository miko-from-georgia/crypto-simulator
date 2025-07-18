package org.example.cryptosimulator.component;

import org.example.cryptosimulator.model.TransactionType;
import org.example.cryptosimulator.repo.TransactionTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TransactionTypeInitializer implements CommandLineRunner {

    private final TransactionTypeRepository transactionTypeRepository;

    public TransactionTypeInitializer(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    @Override
    public void run(String... args) {
        if (transactionTypeRepository.count() == 0) {
            transactionTypeRepository.save(new TransactionType("Пополнение"));
            transactionTypeRepository.save(new TransactionType("Покупка валюты"));
            transactionTypeRepository.save(new TransactionType("Перевод"));
            transactionTypeRepository.save(new TransactionType("Конвертация"));
            transactionTypeRepository.save(new TransactionType("Снятие"));
        }
    }
}

