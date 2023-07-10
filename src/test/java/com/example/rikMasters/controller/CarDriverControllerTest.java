package com.example.rikMasters.controller;

import com.example.rikMasters.entity.Car;
import com.example.rikMasters.entity.Detail;
import com.example.rikMasters.entity.Driver;
import com.example.rikMasters.service.DriverCarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
@SpringBootTest
public class CarDriverControllerTest {

    @MockBean
    private DriverCarService driverCarService;

    @Autowired
    private CarDriverController carDriverController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCar() {
        int carId = 1;
        Car car = new Car(carId, "VIN111222", "О321ЖК", "BMW", "M5", 2011);
        when(driverCarService.getCar(carId)).thenReturn(car);

        Car result = carDriverController.getCar(carId);

        assertEquals(car, result);
        verify(driverCarService).getCar(carId);
    }

    @Test
    public void testGetCars() {
        int page = 0;
        int size = 15;
        String sort = "id";

        Car car1 = new Car(1, "VIN123321", "К268ТН", "KIA", "RIO", 2014);
        Car car2 = new Car(2, "VIN456653", "Л489ЕТ", "AUDI", "TT", 2006);
        List<Car> cars = Arrays.asList(car1, car2);
        when(driverCarService.getCars(page, size, sort)).thenReturn(cars);

        List<Car> result = carDriverController.getCars(page, size, sort);

        assertEquals(cars, result);
        verify(driverCarService).getCars(page, size, sort);
    }

    @Test
    void testCreateCar() {
        Car car = new Car();
        when(driverCarService.createCar(car)).thenReturn(car);

        Car createdCar = carDriverController.createCar(car);

        assertNotNull(createdCar);
        assertEquals(car, createdCar);
        verify(driverCarService).createCar(car);
    }

    @Test
    void testUpdateCar() {
        int carId = 1;
        Car updatedCar = new Car();
        when(driverCarService.updateCar(carId, updatedCar)).thenReturn(updatedCar);

        Car modifiedCar = carDriverController.updateCar(carId, updatedCar);

        assertNotNull(modifiedCar);
        assertEquals(updatedCar, modifiedCar);
        verify(driverCarService).updateCar(carId, updatedCar);
    }

    @Test
    void testDeleteCar() {
        int carId = 1;

        assertDoesNotThrow(() -> carDriverController.deleteCar(carId));
        verify(driverCarService).deleteCar(carId);
    }

    @Test
    void testGetDriver() {
        int driverId = 1;
        Driver driver = new Driver();
        when(driverCarService.getDriver(driverId)).thenReturn(driver);

        Driver result = carDriverController.getDriver(driverId);

        assertEquals(driver, result);
        verify(driverCarService).getDriver(driverId);
    }

    @Test
    void testGetDrivers() {
        int page = 0;
        int size = 15;
        String sort = "id";

        Driver driver1 = new Driver(1, "Иванов Иван Иванович", "60 06 321123", "B2", LocalDate.of(1998, 12, 1), 3);
        Driver driver2 = new Driver(2, "Петров Петр Петрович", "62 26 123321", "B1", LocalDate.of(1988, 10, 15), 7);
        List<Driver> drivers = Arrays.asList(driver1, driver2);

        when(driverCarService.getDrivers(page, size, sort)).thenReturn(drivers);

        List<Driver> result = carDriverController.getDrivers(page, size, sort);

        assertEquals(drivers, result);
        verify(driverCarService).getDrivers(page, size, sort);
    }

    @Test
    void testCreateDriver() {
        Driver driver = new Driver();
        when(driverCarService.createDriver(driver)).thenReturn(driver);

        Driver createdDriver = carDriverController.createDriver(driver);

        assertNotNull(createdDriver);
        assertEquals(driver, createdDriver);
        verify(driverCarService).createDriver(driver);
    }

    @Test
    void testUpdateDriver() {
        int driverId = 1;
        Driver updatedDriver = new Driver();
        when(driverCarService.updateDriver(driverId, updatedDriver)).thenReturn(updatedDriver);

        Driver modifiedDriver = carDriverController.updateDriver(driverId, updatedDriver);

        assertNotNull(modifiedDriver);
        assertEquals(updatedDriver, modifiedDriver);
        verify(driverCarService).updateDriver(driverId, updatedDriver);
    }

    @Test
    void testDeleteDriver() {
        int driverId = 1;

        assertDoesNotThrow(() -> carDriverController.deleteDriver(driverId));
        verify(driverCarService).deleteDriver(driverId);
    }

    @Test
    void testAssigningCarToDriver() {
        int driverId = 1;
        int carId = 1;

        assertDoesNotThrow(() -> carDriverController.assigningCarToDriver(driverId, carId));
        verify(driverCarService).assigningCarToDriver(driverId, carId);
    }

    @Test
    void testAddDetailToCar() {
        int carId = 1;
        Detail detail = new Detail();

        assertDoesNotThrow(() -> carDriverController.addDetailToCar(carId, detail));
        verify(driverCarService).addDetailToCar(carId, detail);
    }
}