package com.example.rikMasters.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "driver")
public class Driver {

    public Driver(Integer id, String fullName, String passport, String licenseCategory, LocalDate birthDate, int workExperience) {
        this.id = id;
        this.fullName = fullName;
        this.passport = passport;
        this.licenseCategory = licenseCategory;
        this.birthDate = birthDate;
        this.workExperience = workExperience;
    }

    public Driver(String fullName, LocalDate birthDate) {
        this.fullName = fullName;
        this.birthDate = birthDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "ФИО не может быть пустым!")
    @Column(name = "fullname")
    private String fullName;

    @NotBlank(message = "Паспорт не может быть пустым!")
    @Column(name = "passport")
    private String passport;

    @NotBlank(message = "Категория прав не может быть пустой!")
    @Column(name = "licensecategory")
    private String licenseCategory;

    @NotBlank(message = "Дата рождения не может быть пустой!")
    @Column(name = "birthdate")
    private LocalDate birthDate;

    @NotBlank(message = "Стаж не может быть пустым!")
    @Column(name = "workexperience")
    private int workExperience;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    private Account account;

    @JsonBackReference
    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    private Car car;
}
