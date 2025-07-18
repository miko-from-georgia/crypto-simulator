package org.example.cryptosimulator.controller;

import org.example.cryptosimulator.model.Account;
import org.example.cryptosimulator.model.Authorization;
import org.example.cryptosimulator.repo.AccountRepository;
import org.example.cryptosimulator.repo.AuthorizationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AccountController {

    private final AccountRepository accountRepository;

    private final AuthorizationRepository authRepository;

    public AccountController(AccountRepository accountRepository, AuthorizationRepository authRepository) {
        this.accountRepository = accountRepository;
        this.authRepository = authRepository;
    }

    @GetMapping("/account")
    public String accountPage(Principal principal, Model model) {
        String login = principal.getName(); // логин текущего пользователя
        Authorization auth = authRepository.findByLogin(login);
        Account account = accountRepository.findByAuthorizationId(auth.getId());

        if (account == null) {
            return "redirect:/login?error=notfound";
        }

        model.addAttribute("account", account);
        return "account"; // templates/account.html
    }
}


