package com.example.rikMasters.repos;

import com.example.rikMasters.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Optional<Driver> findById(Integer id);

    void deleteById(Integer id);

    List<Driver> findByBirthDate(LocalDate birthDate);

    @Query("select d from Driver d where month(d.birthDate) = :month and day(d.birthDate) = :day")
    List<Driver> findByBirthDate(@Param("month") int month, @Param("day") int day);


}
