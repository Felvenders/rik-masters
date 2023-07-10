package com.example.rikMasters.service;

import com.example.rikMasters.entity.Car;
import com.example.rikMasters.entity.Detail;
import com.example.rikMasters.entity.Driver;
import com.example.rikMasters.repos.CarRepository;
import com.example.rikMasters.repos.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
@SpringBootTest
public class DriverCarServiceTest {

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private DriverRepository driverRepository;

    @Autowired
    private DriverCarService driverCarService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCar() {
        int carId = 1;
        Car car = new Car();
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        Car result = driverCarService.getCar(carId);

        assertEquals(car, result);
        verify(carRepository).findById(carId);
    }

    @Test
    public void testGetCars() {
        int page = 0;
        int size = 15;
        String sort = "id";
        List<Car> cars = new ArrayList<>();
        when(carRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(cars));

        List<Car> result = driverCarService.getCars(page, size, sort);

        assertEquals(cars, result);
        verify(carRepository).findAll(any(PageRequest.class));
    }

    @Test
    public void testCreateCar() {
        Car car = new Car();
        when(carRepository.save(car)).thenReturn(car);

        Car result = driverCarService.createCar(car);

        assertEquals(car, result);
        verify(carRepository).save(car);
    }

    @Test
    public void testUpdateCar() {
        int carId = 1;
        Car updatedCar = new Car();
        Car existingCar = new Car();
        when(carRepository.findById(carId)).thenReturn(Optional.of(existingCar));
        when(carRepository.save(existingCar)).thenReturn(updatedCar);

        Car result = driverCarService.updateCar(carId, updatedCar);

        assertEquals(updatedCar, result);
        verify(carRepository).findById(carId);
        verify(carRepository).save(existingCar);
    }

    @Test
    public void testDeleteCar() {
        int carId = 1;

        driverCarService.deleteCar(carId);

        verify(carRepository).deleteById(carId);
    }

    @Test
    public void testGetDriver() {
        int driverId = 1;
        Driver driver = new Driver();
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));

        Driver result = driverCarService.getDriver(driverId);

        assertEquals(driver, result);
        verify(driverRepository).findById(driverId);
    }

    @Test
    public void testGetDrivers() {
        int page = 0;
        int size = 15;
        String sort = "id";
        List<Driver> drivers = new ArrayList<>();
        when(driverRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(drivers));

        List<Driver> result = driverCarService.getDrivers(page, size, sort);

        assertEquals(drivers, result);
        verify(driverRepository).findAll(any(PageRequest.class));
    }

    @Test
    public void testCreateDriver() {
        Driver driver = new Driver();
        when(driverRepository.save(driver)).thenReturn(driver);

        Driver result = driverCarService.createDriver(driver);

        assertEquals(driver, result);
        verify(driverRepository).save(driver);
    }

    @Test
    public void testUpdateDriver() {
        int driverId = 1;
        Driver updatedDriver = new Driver();
        Driver existingDriver = new Driver();
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(existingDriver));
        when(driverRepository.save(existingDriver)).thenReturn(updatedDriver);

        Driver result = driverCarService.updateDriver(driverId, updatedDriver);

        assertEquals(updatedDriver, result);
        verify(driverRepository).findById(driverId);
        verify(driverRepository).save(existingDriver);
    }

    @Test
    public void testDeleteDriver() {
        int driverId = 1;

        driverCarService.deleteDriver(driverId);

        verify(driverRepository).deleteById(driverId);
    }

    @Test
    public void testAssignCarToDriver() {
        int driverId = 1;
        int carId = 1;
        Driver driver = new Driver();
        Car car = new Car();
        when(driverRepository.findById(driverId)).thenReturn(Optional.of(driver));
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        driverCarService.assigningCarToDriver(driverId, carId);

        assertEquals(driver, car.getDriver());
        verify(driverRepository).findById(driverId);
        verify(carRepository).findById(carId);
        verify(carRepository).save(car);
    }

    @Test
    public void testAddDetailToCar() {
        int carId = 1;
        Car car = new Car();
        Detail detail = new Detail();
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        driverCarService.addDetailToCar(carId, detail);

        assertTrue(car.getDetails().contains(detail));
        verify(carRepository).findById(carId);
        verify(carRepository).save(car);
    }
}