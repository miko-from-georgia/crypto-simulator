package org.example.cryptosimulator.service;

import org.example.cryptosimulator.model.Currency;
import org.example.cryptosimulator.repo.CurrencyRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyUpdateService {

    private final CurrencyRepository currencyRepository;

    public CurrencyUpdateService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void ensureUsdCurrencyExists() {
        Optional<Currency> existing = currencyRepository.findByCode("USD");
        if (existing.isEmpty()) {
            Currency usd = new Currency();
            usd.setCode("USD");
            usd.setPriceUsd(1.0);
            currencyRepository.save(usd);
            System.out.println("USD добавлен в базу.");
        } else {
            System.out.println("USD уже есть в базе.");
        }
    }
}
