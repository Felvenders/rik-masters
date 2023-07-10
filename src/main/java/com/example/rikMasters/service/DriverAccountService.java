package com.example.rikMasters.service;

import com.example.rikMasters.entity.Account;
import com.example.rikMasters.entity.Driver;
import com.example.rikMasters.repos.AccountRepository;
import com.example.rikMasters.repos.DriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DriverAccountService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private AccountRepository accountRepository;

    Logger logger = LoggerFactory.getLogger(DriverAccountService.class);

    // Поздравление водителя с днем рождения
    @Scheduled(cron = "0 0 8 * * *") // Запуск каждый день в 8:00 утра
    public void congratulateDrivers() {
        LocalDate currentDate = LocalDate.now();
        List<Driver> drivers = driverRepository.findByBirthDate(currentDate.getMonthValue(), currentDate.getDayOfMonth());
        drivers.stream()
                .map(driver -> "С днем рождения, " + driver.getFullName() + "!!!")
                .forEach(logger::info);
    }

    // Создание аккаута для ведения счета
    public Account createDriverAccount(Account account) {
        return accountRepository.save(account);
    }

    // Пополнение счета
    public void income(Integer accountId, BigDecimal amount, String currency) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Аккаунт с id = " + accountId +  " не найден"));

        BigDecimal convertedAmount = convert(amount, account.getCurrency(), currency);
        account.income(convertedAmount);

        accountRepository.save(account);
    }

    // Снятие со счета
    public void expense(Integer accountId, BigDecimal amount, String currency) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Аккаунт с id = " + accountId +  " не найден"));

        BigDecimal convertedAmount = convert(amount, account.getCurrency(), currency);
        account.expense(convertedAmount);

        accountRepository.save(account);
    }

    // Баланс аккаунта
    public BigDecimal getAccountBalance(Integer accountId, String currency) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Аккаунт с id = " + accountId +  " не найден"));

        return convert(account.getBalance(), account.getCurrency(), currency);
    }

    // Конвертация валют
    public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency) {
        BigDecimal convertedAmount;

        if (fromCurrency.equals(toCurrency)) {
            // Если валюты совпадают, возвращаем исходную сумму
            convertedAmount = amount;
        } else if (fromCurrency.equals("red") && toCurrency.equals("green")) {
            // Конвертация из красных долларов в зеленые доллары
            convertedAmount = amount.multiply(new BigDecimal("2.5"));
        } else if (fromCurrency.equals("green") && toCurrency.equals("blue")) {
            // Конвертация из зеленых долларов в синие доллары
            convertedAmount = amount.multiply(new BigDecimal("0.6"));
        } else {
            // Неподдерживаемая конвертация валют
            throw new IllegalArgumentException("Неподдерживаемая конвертация валют: " + fromCurrency + " в " + toCurrency);
        }

        return convertedAmount;
    }
}
