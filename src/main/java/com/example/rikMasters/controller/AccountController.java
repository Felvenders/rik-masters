package com.example.rikMasters.controller;

import com.example.rikMasters.entity.Account;
import com.example.rikMasters.service.DriverAccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AccountController {

    @Autowired
    private DriverAccountService driverAccountService;

    @Operation(summary = "Congratulations to the driver on his birthday")
    @GetMapping("/congratulateDriver")
    public void congratulateDriver() {
        driverAccountService.congratulateDrivers();
    }

    @Operation(summary = "Creating a new driver account")
    @PostMapping("/account")
    public Account createDriverAccount(@Valid @RequestBody Account account) {
        return driverAccountService.createDriverAccount(account);
    }

    @Operation(summary = "Account refill")
    @PostMapping("/{accountId}/income")
    public void income(@PathVariable("accountId") Integer accountId,
                       @RequestParam("amount")BigDecimal amount,
                       @RequestParam("currency") String currency) {

        driverAccountService.income(accountId, amount, currency);
    }

    @Operation(summary = "Withdrawal from the account")
    @PostMapping("/{accountId}/expense")
    public void expense(@PathVariable("accountId") Integer accountId,
                        @RequestParam("amount")BigDecimal amount,
                        @RequestParam("currency") String currency) {

        driverAccountService.expense(accountId, amount, currency);
    }

    @Operation(summary = "Get account balance")
    @GetMapping("/{accountId}/balance")
    public BigDecimal getAccountBalance(@PathVariable("accountId") Integer accountId,
                                        @RequestParam("currency") String currency) {

        return driverAccountService.getAccountBalance(accountId, currency);
    }
}
