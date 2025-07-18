package org.example.cryptosimulator.controller;

import org.example.cryptosimulator.model.Currency;
import org.example.cryptosimulator.repo.CurrencyRepository;
import org.example.cryptosimulator.service.CurrencyLoadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyRepository currencyRepository;

    private final CurrencyLoadService currencyLoadService;

    public CurrencyController(CurrencyRepository currencyRepository, CurrencyLoadService currencyLoadService) {
        this.currencyRepository = currencyRepository;
        this.currencyLoadService = currencyLoadService;
    }

    @GetMapping("/list")
    public String showCurrencies(Model model) {

        List<Currency> currencies = currencyRepository.findAll();

        model.addAttribute("currencies", currencies);
        return "currency-list";
    }

    @GetMapping("/load-top")
    public String loadTop10() {
        currencyLoadService.loadTop10Currencies();
        return "redirect:/currency/list";
    }
}


