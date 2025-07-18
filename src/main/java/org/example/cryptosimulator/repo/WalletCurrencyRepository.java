package org.example.cryptosimulator.repo;

import org.example.cryptosimulator.model.WalletCurrency;
import org.example.cryptosimulator.model.Wallet;
import org.example.cryptosimulator.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletCurrencyRepository extends JpaRepository<WalletCurrency, Long> {
     Optional<WalletCurrency> findByWalletAndCurrency(Wallet wallet, Currency currency);
}

