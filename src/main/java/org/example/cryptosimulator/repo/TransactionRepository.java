package org.example.cryptosimulator.repo;

import org.example.cryptosimulator.model.Account;
import org.example.cryptosimulator.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount(Account account);
}

