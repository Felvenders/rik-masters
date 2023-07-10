package com.example.rikMasters.controller;

import com.example.rikMasters.entity.Account;
import com.example.rikMasters.service.DriverAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
@SpringBootTest
public class AccountControllerTest {

    @MockBean
    private DriverAccountService driverAccountService;

    @Autowired
    private AccountController accountController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCongratulateDriver() {
        assertDoesNotThrow(() -> accountController.congratulateDriver());
        verify(driverAccountService).congratulateDrivers();
    }

    @Test
    public void testCreateDriverAccount() {
        Account account = new Account();
        when(driverAccountService.createDriverAccount(account)).thenReturn(account);

        Account createdAccount = accountController.createDriverAccount(account);

        assertNotNull(createdAccount);
        assertEquals(account, createdAccount);
        verify(driverAccountService).createDriverAccount(account);
    }

    @Test
    public void testIncome() {
        int accountId = 1;
        BigDecimal amount = BigDecimal.valueOf(1000);
        String currency = "blue";

        assertDoesNotThrow(() -> accountController.income(accountId, amount, currency));
        verify(driverAccountService).income(accountId, amount, currency);
    }

    @Test
    public void testExpense() {
        int accountId = 1;
        BigDecimal amount = BigDecimal.valueOf(1000);
        String currency = "blue";

        assertDoesNotThrow(() -> accountController.expense(accountId, amount, currency));
        verify(driverAccountService).expense(accountId, amount, currency);
    }

    @Test
    public void testGetBalance() {
        int accountId = 1;
        String currency = "red";

        assertDoesNotThrow(() -> accountController.getAccountBalance(accountId, currency));
        verify(driverAccountService).getAccountBalance(accountId, currency);
    }
}
