package org.example.cryptosimulator.repo;

import org.example.cryptosimulator.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    boolean existsByCoingeckoId(String coingeckoId);

    Optional<Currency> findByCode(String code);

}


