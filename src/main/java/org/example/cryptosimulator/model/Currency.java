package org.example.cryptosimulator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private String coingeckoId;

    private Double priceUsd;

    private LocalDateTime updatedAt;

    public Currency() {
    }

    public Currency(String code, String name, String coingeckoId, Double priceUsd) {
        this.code = code;
        this.name = name;
        this.coingeckoId = coingeckoId;
        this.priceUsd = priceUsd;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoingeckoId() {
        return coingeckoId;
    }

    public void setCoingeckoId(String coingeckoId) {
        this.coingeckoId = coingeckoId;
    }

    public Double getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(Double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
