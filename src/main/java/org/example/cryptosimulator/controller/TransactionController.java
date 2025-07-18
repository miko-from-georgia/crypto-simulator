package org.example.cryptosimulator.controller;

import org.example.cryptosimulator.model.Account;
import org.example.cryptosimulator.model.Transaction;
import org.example.cryptosimulator.service.AccountService;
import org.example.cryptosimulator.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public String showTransactions(Model model, Principal principal) {
        Account account = accountService.findByLogin(principal.getName());
        List<Transaction> transactions = transactionService.findByAccount(account);
        model.addAttribute("transactions", transactions);
        return "transactions";
    }
}

