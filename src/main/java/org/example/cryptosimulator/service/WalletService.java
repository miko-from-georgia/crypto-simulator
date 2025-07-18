package org.example.cryptosimulator.service;

import org.example.cryptosimulator.model.*;
import org.example.cryptosimulator.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class WalletService {

    private final AccountService accountService;

    private final WalletRepository walletRepository;

    private final CurrencyRepository currencyRepository;

    private final WalletCurrencyRepository walletCurrencyRepository;

    private final TransactionTypeRepository transactionTypeRepository;

    private final TransactionRepository transactionRepository;

    public WalletService(AccountService accountService, WalletRepository walletRepository, CurrencyRepository currencyRepository, WalletCurrencyRepository walletCurrencyRepository, TransactionTypeRepository transactionTypeRepository, TransactionRepository transactionRepository) {
        this.accountService = accountService;
        this.walletRepository = walletRepository;
        this.currencyRepository = currencyRepository;
        this.walletCurrencyRepository = walletCurrencyRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionRepository = transactionRepository;
    }

    public void depositUsd(String login, Double amount) {
        Account account = accountService.findByLogin(login);
        Wallet wallet = account.getWallet();
        wallet.addUsd(amount);
        walletRepository.save(wallet);
        // Сохраняем транзакцию
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setCurrency(null);

        TransactionType type = transactionTypeRepository.findByName("Пополнение").orElseThrow(() -> new RuntimeException("Тип 'Пополнение' не найден"));

        transaction.setType(type);
        transactionRepository.save(transaction);
    }

    public Wallet getWallet(String login) {
        return accountService.findByLogin(login).getWallet();
    }

    @Transactional
    public void buyCurrency(String login, String currencyCode, double usdAmount) {
        Account account = accountService.findByLogin(login);
        Wallet wallet = account.getWallet();

        if (wallet.getUsdBalance() < usdAmount) {
            throw new IllegalArgumentException("Недостаточно средств на USD-счёте.");
        }

        Currency currency = currencyRepository.findByCode(currencyCode).orElseThrow(() -> new IllegalArgumentException("Валюта не найдена: " + currencyCode));

        double amountToAdd = usdAmount / currency.getPriceUsd();

        WalletCurrency walletCurrency = wallet.getCurrencies().stream().filter(wc -> wc.getCurrency().getCode().equals(currencyCode)).findFirst().orElseGet(() -> {
            WalletCurrency newCurrency = new WalletCurrency();
            newCurrency.setCurrency(currency);
            newCurrency.setWallet(wallet);
            newCurrency.setAmount(0.0);
            wallet.getCurrencies().add(newCurrency);
            return newCurrency;
        });

        walletCurrency.setAmount(walletCurrency.getAmount() + amountToAdd);

        wallet.setUsdBalance(wallet.getUsdBalance() - usdAmount);

        walletCurrencyRepository.save(walletCurrency);
        walletRepository.save(wallet);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setCurrency(currency);
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(usdAmount);

        TransactionType type = transactionTypeRepository.findByName("Покупка валюты").orElseThrow(() -> new RuntimeException("Тип 'Покупка' не найден"));

        transaction.setType(type);
        transactionRepository.save(transaction);
    }

    @Transactional
    public double convertCurrency(String login, String fromCode, String toCode, double amount) {
        Account account = accountService.findByLogin(login);
        Wallet wallet = account.getWallet();

        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть больше нуля");
        }

        Currency fromCurrency = currencyRepository.findByCode(fromCode).orElseThrow(() -> new IllegalArgumentException("Валюта не найдена: " + fromCode));
        Currency toCurrency = currencyRepository.findByCode(toCode).orElseThrow(() -> new IllegalArgumentException("Валюта не найдена: " + toCode));

        WalletCurrency fromWalletCurrency = wallet.getCurrencies().stream().filter(wc -> wc.getCurrency().getCode().equals(fromCode)).findFirst().orElseThrow(() -> new IllegalArgumentException("У вас нет валюты " + fromCode));

        if (fromWalletCurrency.getAmount() < amount) {
            throw new IllegalArgumentException("Недостаточно валюты " + fromCode);
        }

        double amountInUsd = amount * fromCurrency.getPriceUsd();
        double toAmount = amountInUsd / toCurrency.getPriceUsd();

        fromWalletCurrency.setAmount(fromWalletCurrency.getAmount() - amount);

        WalletCurrency toWalletCurrency = wallet.getCurrencies().stream().filter(wc -> wc.getCurrency().getCode().equals(toCode)).findFirst().orElseGet(() -> {
            WalletCurrency newCurrency = new WalletCurrency();
            newCurrency.setCurrency(toCurrency);
            newCurrency.setWallet(wallet);
            newCurrency.setAmount(0.0);
            wallet.getCurrencies().add(newCurrency);
            return newCurrency;
        });
        toWalletCurrency.setAmount(toWalletCurrency.getAmount() + toAmount);

        walletCurrencyRepository.save(fromWalletCurrency);
        walletCurrencyRepository.save(toWalletCurrency);
        walletRepository.save(wallet);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setCurrency(fromCurrency);
        TransactionType type = transactionTypeRepository.findByName("Конвертация").orElseThrow(() -> new RuntimeException("Тип 'Конвертация' не найден"));
        transaction.setType(type);

        transactionRepository.save(transaction);

        return toAmount;
    }

    @Transactional
    public void withdrawUsd(String login, double amount) {
        Account account = accountService.findByLogin(login);
        Wallet wallet = account.getWallet();

        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть больше нуля");
        }

        if (wallet.getUsdBalance() < amount) {
            throw new IllegalArgumentException("Недостаточно средств на USD-счёте.");
        }

        wallet.setUsdBalance(wallet.getUsdBalance() - amount);
        walletRepository.save(wallet);

        Currency usdCurrency = currencyRepository.findByCode("USD").orElseThrow(() -> new IllegalArgumentException("Валюта USD не найдена"));

        TransactionType type = transactionTypeRepository.findByName("Снятие").orElseThrow(() -> new RuntimeException("Тип 'Снятие' не найден"));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setCurrency(usdCurrency);
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(amount);
        transaction.setType(type);

        transactionRepository.save(transaction);
    }

}

