package com.smartfarming.iot.Repository;

import com.smartfarming.iot.Data.Model.Sensor;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    @Query(value = "SELECT * FROM sensor WHERE code = :code ORDER BY timestamp ASC LIMIT 1", nativeQuery = true)
    Optional<Sensor> findLatestByCode(@Param("code") String code);

    @Query(value = "SELECT * FROM sensor WHERE code = :code ORDER BY timestamp DESC", nativeQuery = true)
    List<Sensor> findAllByCode(@Param("code") String code);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sensor WHERE code = :code", nativeQuery = true)
    int deleteAllByCode(@Param("code") String code);


}
