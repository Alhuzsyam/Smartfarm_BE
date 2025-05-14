package com.smartfarming.iot.Service;


import com.smartfarming.iot.Data.Model.Sensor;
import com.smartfarming.iot.Repository.SensorRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorServices {

    @Autowired
    private SensorRepository sensorRepository;

    // Save sensor data
    public Sensor saveSensorData(Sensor sensorData) {
        return sensorRepository.save(sensorData);
    }

    // Get all sensor data
    public List<Sensor> getAllSensorData() {
        return sensorRepository.findAll();
    }

    // Get sensor data by ID
    public Optional<Sensor> getSensorDataById(Long id) {
        return sensorRepository.findById(id);
    }

    // Delete sensor data by ID
    public void deleteSensorDataById(Long id) {
        sensorRepository.deleteById(id);
    }
    //get last data
    public Optional<Sensor> getLatestSensorDataByCodeKey(String code) {
        return sensorRepository.findLatestByCode(code);
    }

     // get all data
    public List<Sensor> getAllSensorDataByCodeKey(String code) {
    return sensorRepository.findAllByCode(code);
    }
    public int deleteAllSensorDataByCode(String code) {
        return sensorRepository.deleteAllByCode(code);
    }
    

}
