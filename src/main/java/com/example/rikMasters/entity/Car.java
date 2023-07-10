package com.example.rikMasters.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "car")
public class Car {

    public Car(String vin, String producer, String brand, int yearOfRelease) {
        this.vin = vin;
        this.producer = producer;
        this.brand = brand;
        this.yearOfRelease = yearOfRelease;
    }

    public Car(Integer id, String vin, String stateNumber, String producer, String brand, int yearOfRelease) {
        this.id = id;
        this.vin = vin;
        this.stateNumber = stateNumber;
        this.producer = producer;
        this.brand = brand;
        this.yearOfRelease = yearOfRelease;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "VIN код не может быть пустым!")
    @Column(name = "vin")
    private String vin;

    @NotBlank(message = "Гос. номер не может быть пустым!")
    @Column(name = "statenumber")
    private String stateNumber;

    @Column(name = "producer")
    private String producer;

    @Column(name = "brand")
    private String brand;

    @Min(value = 1960, message = "Год выпуска должен быть больше или равен 1960 году")
    @Column(name = "yearofrelease")
    private int yearOfRelease;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @JsonManagedReference
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Detail> details = new ArrayList<>();
}
