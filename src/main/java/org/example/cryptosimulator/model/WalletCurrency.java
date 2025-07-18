package org.example.cryptosimulator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class WalletCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    private double amount;

    public WalletCurrency() {
    }

    public WalletCurrency(Long id, Wallet wallet, Currency currency, double amount) {
        this.id = id;
        this.wallet = wallet;
        this.currency = currency;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

