package com.smartfarming.iot.Controller;

import com.smartfarming.iot.Data.Dto.Response;
import com.smartfarming.iot.Data.Model.Sensor;
import com.smartfarming.iot.Service.SensorServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/sensor")
public class SensorController {

    @Autowired
    SensorServices sensorService;

    @PostMapping("/create")
    public Response<Object> createSensorData(@RequestBody Sensor sensorData) {
        Response<Object> res = new Response<>();

        try {
            Sensor savedData = sensorService.saveSensorData(sensorData);
            res.setStatus(HttpStatus.CREATED.toString());
            res.setMessage("Sensor data saved successfully.");
            res.setPayload(savedData);
        } catch (Exception e) {
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            res.setMessage("Failed to save sensor data: " + e.getMessage());
            res.setPayload(null);
        }

        return res;
    }

    @GetMapping("/latest")
    public Response<Object> getLatestSensorDataByApiKey(@RequestParam String code) {
    Response<Object> res = new Response<>();

    try {
        Optional<Sensor> latestData = sensorService.getLatestSensorDataByCodeKey(code);

        if (latestData.isPresent()) {
            res.setStatus(HttpStatus.OK.toString());
            res.setMessage("Latest sensor data retrieved successfully.");
            res.setPayload(latestData.get());
        } else {
            res.setStatus(HttpStatus.NOT_FOUND.toString());
            res.setMessage("No sensor data found for the given API key.");
            res.setPayload(null);
        }

    } catch (Exception e) {
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        res.setMessage("Failed to retrieve latest sensor data: " + e.getMessage());
        res.setPayload(null);
    }

    return res;
}

@GetMapping("/allbycode")
public Response<Object> getAllSensorDataByApiKey(@RequestParam String code) {
    Response<Object> res = new Response<>();

    try {
        List<Sensor> allData = sensorService.getAllSensorDataByCodeKey(code);

        if (!allData.isEmpty()) {
            res.setStatus(HttpStatus.OK.toString());
            res.setMessage("Sensor data retrieved successfully.");
            res.setPayload(allData);
        } else {
            res.setStatus(HttpStatus.NOT_FOUND.toString());
            res.setMessage("No sensor data found for the given API key.");
            res.setPayload(null);
        }

    } catch (Exception e) {
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        res.setMessage("Failed to retrieve sensor data: " + e.getMessage());
        res.setPayload(null);
    }

    return res;
}



    @GetMapping("/all")
    public Response<Object> getAllSensorData() {
        Response<Object> res = new Response<>();

        try {
            List<Sensor> dataList = sensorService.getAllSensorData();
            res.setStatus(HttpStatus.OK.toString());
            res.setMessage("Sensor data fetched successfully.");
            res.setPayload(dataList);
        } catch (Exception e) {
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            res.setMessage("Failed to fetch sensor data: " + e.getMessage());
            res.setPayload(null);
        }

        return res;
    }

    @DeleteMapping("/deletebycode")
    public Response<Object> deleteSensorDataByCode(@RequestParam String code) {
        Response<Object> res = new Response<>();

        try {
            int deletedCount = sensorService.deleteAllSensorDataByCode(code);

            if (deletedCount > 0) {
                res.setStatus(HttpStatus.OK.toString());
                res.setMessage("Sensor data deleted successfully.");
                res.setPayload(deletedCount + " record(s) deleted.");
            } else {
                res.setStatus(HttpStatus.NOT_FOUND.toString());
                res.setMessage("No sensor data found for the given code.");
                res.setPayload(null);
            }
        } catch (Exception e) {
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            res.setMessage("Failed to delete sensor data: " + e.getMessage());
            res.setPayload(null);
        }

        return res;
    }
}
