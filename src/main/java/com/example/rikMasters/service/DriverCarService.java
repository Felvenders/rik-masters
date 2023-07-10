package com.example.rikMasters.service;

import com.example.rikMasters.entity.Car;
import com.example.rikMasters.entity.Detail;
import com.example.rikMasters.entity.Driver;
import com.example.rikMasters.repos.CarRepository;
import com.example.rikMasters.repos.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DriverCarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverRepository driverRepository;

    // Поиск автомобиля по id
    public Car getCar(Integer carId) {
        return carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("Автомобиль с id = " + carId + " не найден"));
    }

    // Поиск списка автомобилей (с пагинацией и сортировкой)
    public List<Car> getCars(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<Car> cars = carRepository.findAll(pageable);

        return cars.getContent();
    }

    // Создание нового автомобиля
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    // Изменение существующего автомобиля
    public Car updateCar(Integer carId, Car updatedCar) {
        Car carFromDb = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Автомобиль с id = " + carId + " не найден"));

        carFromDb.setVin(updatedCar.getVin());
        carFromDb.setStateNumber(updatedCar.getStateNumber());
        carFromDb.setProducer(updatedCar.getProducer());
        carFromDb.setBrand(updatedCar.getBrand());
        carFromDb.setYearOfRelease(updatedCar.getYearOfRelease());
//        carFromDb.setDetails(updatedCar.getDetails());

        return carRepository.save(carFromDb);
    }

    // Удаление автомобиля
    public void deleteCar(Integer carId) {
        carRepository.deleteById(carId);
    }

    // Поиск водителя по id
    public Driver getDriver(Integer driverId) {
        return driverRepository.findById(driverId).orElseThrow(() -> new EntityNotFoundException("Водитель с id = " + driverId + " не найден"));
    }

    // Поиск списка водителей (с пагинацией и сортировкой)
    public List<Driver> getDrivers(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<Driver> drivers = driverRepository.findAll(pageable);

        return drivers.getContent();
    }

    // Создание нового водителя
    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    // Изменение существующего водителя
    public Driver updateDriver(Integer driverId, Driver updatedDriver) {
        Driver driverFromDb = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Водитель с id = " + driverId + " не найден"));

        driverFromDb.setFullName(updatedDriver.getFullName());
        driverFromDb.setPassport(updatedDriver.getPassport());
        driverFromDb.setLicenseCategory(updatedDriver.getLicenseCategory());
        driverFromDb.setBirthDate(updatedDriver.getBirthDate());
        driverFromDb.setWorkExperience(updatedDriver.getWorkExperience());

        return driverRepository.save(driverFromDb);
    }

    // Удаление водителя
    public void deleteDriver(Integer driverId) {
        driverRepository.deleteById(driverId);
    }

    // Присвоение автомобиля водителю
    public void assigningCarToDriver(Integer driverId, Integer carId) {

        // Поиск водителя по id
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Водитель с id = " + driverId + " не найден"));

        // Поиск автомобиля по id
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Автомобиль с id = " + carId + " не найден"));

        // Проверяем, не владеет ли водитель данным автомобилем
        if (driver.getCar() != null && driver.getCar().getId().equals(car.getId())) {
            throw new IllegalArgumentException("Автомобиль с id = " + carId + " уже принадлежит водителю");
        }

        // Проверяем, не владеет ли автомобилем другой водитель
        if (car.getDriver() != null && !car.getDriver().getId().equals(driver.getId())) {
            throw new IllegalArgumentException("Автомобиль уже принадлежит другому водителю");
        }

        // Присвоение автомобиля и водителя
        driver.setCar(car);
        car.setDriver(driver);

        // Сохранение в БД
        driverRepository.save(driver);
        carRepository.save(car);
    }

    // Добавление детали к автомобилю
    public void addDetailToCar(Integer carId, Detail detail) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Автомобиль с id = " + carId + " не найден"));

        car.getDetails().add(detail);
        carRepository.save(car);
    }
}
