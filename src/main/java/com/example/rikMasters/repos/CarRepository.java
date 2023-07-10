package com.example.rikMasters.repos;

import com.example.rikMasters.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Integer> {

    Optional<Car> findById(Integer id);

    void deleteById(Integer id);

    List<Car> findByProducer(String producer);

    List<Car> findByYearOfRelease(int yearOfRelease);

    List<Car> findByVin(String vin);

    List<Car> findByBrand(String brand);
}
