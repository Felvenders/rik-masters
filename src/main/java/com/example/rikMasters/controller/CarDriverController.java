package com.example.rikMasters.controller;

import com.example.rikMasters.entity.Car;
import com.example.rikMasters.entity.Detail;
import com.example.rikMasters.entity.Driver;
import com.example.rikMasters.service.DriverCarService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarDriverController {

    @Autowired
    private DriverCarService driverCarService;

    @Operation(summary = "Get a car by id")
    @GetMapping("/cars/{carId}")
    public Car getCar(@PathVariable("carId") Integer carId) {
        return driverCarService.getCar(carId);
    }

    @Operation(summary = "Search for a list of cars (with pagination and sorting)")
    @GetMapping("/cars")
    public List<Car> getCars(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "15") int size,
                             @RequestParam(defaultValue = "id") String sort) {

        return driverCarService.getCars(page, size, sort);
    }

    @Operation(summary = "Creating a new car")
    @PostMapping("/cars")
    public Car createCar(@Valid @RequestBody Car car) {
        return driverCarService.createCar(car);
    }

    @Operation(summary = "Changing an existing car")
    @PutMapping("/cars/{carId}")
    public Car updateCar(@PathVariable("carId") Integer carId, @Valid @RequestBody Car updatedCar) {
        return driverCarService.updateCar(carId, updatedCar);
    }

    @Operation(summary = "Removing the car")
    @DeleteMapping("/cars/{carId}")
    public void deleteCar(@PathVariable("carId") Integer carId) {
        driverCarService.deleteCar(carId);
    }

    @Operation(summary = "Get a driver by id")
    @GetMapping("/drivers/{driverId}")
    public Driver getDriver(@PathVariable("driverId") Integer driverId) {
        return driverCarService.getDriver(driverId);
    }

    @Operation(summary = "Search for a list of drivers (with pagination and sorting)")
    @GetMapping("/drivers")
    public List<Driver> getDrivers(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "15") int size,
                                   @RequestParam(defaultValue = "id") String sort) {

        return driverCarService.getDrivers(page, size, sort);
    }

    @Operation(summary = "Creating a new driver")
    @PostMapping("/drivers")
    public Driver createDriver(@Valid @RequestBody Driver driver) {
        return driverCarService.createDriver(driver);
    }

    @Operation(summary = "Changing an existing driver")
    @PutMapping("/drivers/{driverId}")
    public Driver updateDriver(@PathVariable("driverId") Integer driverId, @Valid @RequestBody Driver updatedDriver) {
        return driverCarService.updateDriver(driverId, updatedDriver);
    }

    @Operation(summary = "Removing the driver")
    @DeleteMapping("/drivers/{driverId}")
    public void deleteDriver(@PathVariable("driverId") Integer driverId) {
        driverCarService.deleteDriver(driverId);
    }

    @Operation(summary = "Assigning a car to a driver")
    @PostMapping("/drivers/{driverId}/cars/{carId}")
    public void assigningCarToDriver(@PathVariable("driverId") Integer driverId, @PathVariable("carId") Integer carId) {
        driverCarService.assigningCarToDriver(driverId, carId);
    }

    @Operation(summary = "Adding a detail to a car")
    @PostMapping("/cars/{carId}/details")
    public void addDetailToCar(@PathVariable("carId") Integer carId, @Valid @RequestBody Detail detail) {
        driverCarService.addDetailToCar(carId, detail);
    }
}
