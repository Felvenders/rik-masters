package com.example.rikMasters.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @NotBlank(message = "Баланс не может быть пустым!")
    @Column(name = "balance")
    private BigDecimal balance;

    @NotBlank(message = "Текущая валюта не может быть пустой!")
    @Column(name = "currency")
    private String currency;

    public void income(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void expense(BigDecimal amount) {
        if (amount.compareTo(balance) > 0) {
            throw new RuntimeException("Недостаточно средств на счете");
        }

        balance = balance.subtract(amount);
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
