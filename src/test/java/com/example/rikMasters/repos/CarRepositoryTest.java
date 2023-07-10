package com.example.rikMasters.repos;

import com.example.rikMasters.entity.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void testFindById() {
        Car car = new Car();
        car.setProducer("Lada");
        car.setYearOfRelease(2011);
        entityManager.persist(car);
        entityManager.flush();

        Optional<Car> foundCar = carRepository.findById(car.getId());

        assertTrue(foundCar.isPresent());
        assertEquals(car.getProducer(), foundCar.get().getProducer());
        assertEquals(car.getYearOfRelease(), foundCar.get().getYearOfRelease());
    }


    @Test
    void testDeleteById() {
        Car car = new Car();
        car.setProducer("Lada");
        car.setYearOfRelease(2011);
        entityManager.persist(car);
        entityManager.flush();

        carRepository.deleteById(car.getId());
        entityManager.flush();

        Optional<Car> foundCar = carRepository.findById(car.getId());
        assertTrue(foundCar.isEmpty());
    }

    @Test
    void testFindByProducer() {
        Car car1 = new Car();
        car1.setProducer("BMW");
        car1.setYearOfRelease(2023);
        entityManager.persist(car1);

        Car car2 = new Car();
        car2.setProducer("Audi");
        car2.setYearOfRelease(2018);
        entityManager.persist(car2);

        Car car3 = new Car();
        car3.setProducer("Audi");
        car3.setYearOfRelease(2009);
        entityManager.persist(car3);

        entityManager.flush();

        List<Car> audiCars = carRepository.findByProducer("Audi");

        assertEquals(2, audiCars.size());
        assertTrue(audiCars.contains(car2));
        assertTrue(audiCars.contains(car3));
    }

    @Test
    void testFindByYearOfRelease() {
        Car car1 = new Car("VIN123321", "BMW", "X6", 2020);
        entityManager.persist(car1);

        Car car2 = new Car("VIN999888", "Lada", "Granta", 2014);
        entityManager.persist(car2);

        entityManager.flush();

        List<Car> cars = carRepository.findByYearOfRelease(2020);

        assertEquals(1, cars.size());
        assertTrue(cars.contains(car1));
    }

    @Test
    void testFindByVin() {
        Car car1 = new Car("VIN123321", "BMW", "X6", 2020);
        entityManager.persist(car1);

        Car car2 = new Car("VIN999888", "Lada", "Granta", 2014);
        entityManager.persist(car2);

        entityManager.flush();

        List<Car> cars = carRepository.findByVin("VIN123321");

        assertEquals(1, cars.size());
        assertTrue(cars.contains(car1));
    }

    @Test
    void testFindByBrand() {
        Car car1 = new Car("VIN123321", "BMW", "X6", 2020);
        entityManager.persist(car1);

        Car car2 = new Car("VIN999888", "Lada", "Granta", 2014);
        entityManager.persist(car2);

        Car car3 = new Car("VIN444555", "Lada", "Granta", 2018);
        entityManager.persist(car3);

        entityManager.flush();

        List<Car> cars = carRepository.findByBrand("Granta");

        assertEquals(2, cars.size());
        assertTrue(cars.contains(car2));
        assertTrue(cars.contains(car3));
    }
}