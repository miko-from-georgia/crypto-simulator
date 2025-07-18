package org.example.cryptosimulator.model;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "authorization_id")
    private Authorization authorization;
    @OneToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    public Account() {
    }

    public Account(Long id, Authorization authorization, Wallet wallet) {
        this.id = id;
        this.authorization = authorization;
        this.wallet = wallet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
