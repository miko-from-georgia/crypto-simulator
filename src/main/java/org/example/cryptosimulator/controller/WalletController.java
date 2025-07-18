package org.example.cryptosimulator.controller;

import org.example.cryptosimulator.model.Account;
import org.example.cryptosimulator.repo.CurrencyRepository;
import org.example.cryptosimulator.service.AccountService;
import org.example.cryptosimulator.service.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/wallet") // Базовый путь для всех методов
public class WalletController {

    private final AccountService accountService;

    private final WalletService walletService;

    private final CurrencyRepository currencyRepository;

    public WalletController(AccountService accountService, WalletService walletService, CurrencyRepository currencyRepository) {
        this.accountService = accountService;
        this.walletService = walletService;
        this.currencyRepository = currencyRepository;
    }

    @PostMapping("/deposit-usd")
    public String depositUsd(@RequestParam Double amount, Principal principal, RedirectAttributes redirectAttributes) {
        if (amount <= 0) {
            redirectAttributes.addFlashAttribute("error", "Сумма должна быть больше 0");
            return "redirect:/wallet";
        }
        walletService.depositUsd(principal.getName(), amount);
        redirectAttributes.addFlashAttribute("success", "USD успешно зачислены");
        return "redirect:/wallet";
    }

    @GetMapping
    public String showWallet(@RequestParam(required = false) String purchaseCurrency, @ModelAttribute("success") String success, @ModelAttribute("error") String error, Model model, Principal principal) {

        Account account = accountService.findByLogin(principal.getName());
        model.addAttribute("wallet", account.getWallet());

        model.addAttribute("currencies", currencyRepository.findAll());

        if (purchaseCurrency != null) {
            model.addAttribute("purchaseCurrency", purchaseCurrency.toUpperCase());
        }

        System.out.println("FLASH SUCCESS: " + success);
        System.out.println("FLASH ERROR: " + error);

        return "wallet";
    }

    @PostMapping("/convert")
    public String convertCurrency(@RequestParam String fromCurrency, @RequestParam String toCurrency, @RequestParam double amount, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            double convertedAmount = walletService.convertCurrency(principal.getName(), fromCurrency, toCurrency, amount);
            redirectAttributes.addFlashAttribute("success", String.format("Конвертировано %.4f %s в %.4f %s", amount, fromCurrency, convertedAmount, toCurrency));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/wallet";
    }

    @PostMapping("/deposit")
    public String buyCurrencyThroughDepositForm(@RequestParam String currency, @RequestParam double amount, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            walletService.buyCurrency(principal.getName(), currency, amount);
            redirectAttributes.addFlashAttribute("success", "Успешно куплено " + currency);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/wallet";
    }

    @PostMapping("/withdraw-usd")
    public String withdrawUsd(@RequestParam double amount, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            walletService.withdrawUsd(principal.getName(), amount);
            redirectAttributes.addFlashAttribute("success", "Снятие " + amount + " USD выполнено успешно.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/wallet";
    }

}

