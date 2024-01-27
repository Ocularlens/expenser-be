package org.ocularlens.expenserbe.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    @GetMapping
    public String retrieveTransactions(Authentication authentication) {
        System.out.println(authentication.getName());
        return "test";
    }
}
