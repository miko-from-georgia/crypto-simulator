package org.example.cryptosimulator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double usdBalance = 0.0;

    @OneToOne(mappedBy = "wallet")
    private Account account;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WalletCurrency> currencies = new ArrayList<>();

    public Wallet() {
        this.usdBalance = 0.0;
    }

    public Wallet(Long id, Account account, List<WalletCurrency> currencies) {
        this.id = id;
        this.account = account;
        this.currencies = currencies;
        this.usdBalance = 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getUsdBalance() {
        return usdBalance;
    }

    public void setUsdBalance(double usdBalance) {
        this.usdBalance = usdBalance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<WalletCurrency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<WalletCurrency> currencies) {
        this.currencies = currencies;
    }

    public void addUsd(double amount) {
        this.usdBalance += amount;
    }

}
