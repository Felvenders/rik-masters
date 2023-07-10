package com.example.rikMasters.service;

import com.example.rikMasters.entity.Account;
import com.example.rikMasters.entity.Driver;
import com.example.rikMasters.repos.AccountRepository;
import com.example.rikMasters.repos.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
@SpringBootTest
public class DriverAccountServiceTest {

    @Mock
    private Logger logger;

    @MockBean
    private DriverRepository driverRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private DriverAccountService driverAccountService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCongratulateDriver() {
        LocalDate currentDate = LocalDate.now();
        Driver driver1 = new Driver("Иван Иванов", LocalDate.of(2001, 7, 9));
        Driver driver2 = new Driver("Петр Петров", LocalDate.of(1980, 7, 9));
        List<Driver> drivers = Arrays.asList(driver1, driver2);

        when(driverRepository.findAll()).thenReturn(drivers);

        driverAccountService.congratulateDrivers();

        verify(logger).info(anyString());
    }

    @Test
    public void testCreateDriverAccount() {
        Account account = new Account();
        when(accountRepository.save(account)).thenReturn(account);

        Account result = driverAccountService.createDriverAccount(account);

        assertEquals(account, result);
        verify(accountRepository).save(account);
    }

    @Test
    public void testIncome() {
        int accountId = 1;
        BigDecimal amount = BigDecimal.valueOf(1000);
        String currency = "red";

        Account account = new Account();
        account.setId(accountId);
        account.setCurrency(currency);
        account.setBalance(BigDecimal.valueOf(1000));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        driverAccountService.income(accountId, amount, currency);

        verify(accountRepository).findById(accountId);
    }

    @Test
    public void testExpense() {
        int accountId = 1;
        BigDecimal amount = BigDecimal.valueOf(2000);
        String currency = "blue";

        Account account = new Account();
        account.setId(accountId);
        account.setCurrency(currency);
        account.setBalance(BigDecimal.valueOf(5000));
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        driverAccountService.expense(accountId, amount, currency);

        verify(accountRepository).findById(accountId);
    }

    @Test
    public void testGetAccountBalance() {
        int accountId = 1;
        String currency = "green";

        Account account = new Account();
        account.setId(accountId);
        account.setCurrency(currency);
        account.setBalance(BigDecimal.valueOf(2000));

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        BigDecimal balance = driverAccountService.getAccountBalance(accountId, currency);

        verify(accountRepository).findById(accountId);
    }

    @Test
    public void testConvert() {
        BigDecimal amount = BigDecimal.valueOf(3000);
        String fromCurrency = "red";
        String toCurrency = "green";

        BigDecimal converted = driverAccountService.convert(amount, fromCurrency, toCurrency);

        BigDecimal expected = amount.multiply(BigDecimal.valueOf(2.5));
        assertEquals(expected, converted);
    }
}
