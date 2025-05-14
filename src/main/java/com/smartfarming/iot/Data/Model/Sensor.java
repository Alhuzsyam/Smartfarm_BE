package com.smartfarming.iot.Data.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String code = null; // API Key

    private LocalDateTime timestamp; // Waktu data dicatat

    private Double lightIntensity;       // Lux
    private Double airTemperature;       // Temperature Ruang
    private Double airHumidity;          // Kelembaban Ruang

    private Double soilTemperature;      // Temperature Tanah
    private Double soilMoisture;         // Kelembaban Tanah
    private Double soilConductivity;     // Konduktivitas Tanah
    private Double soilPh;               // pH Tanah

    private Double nitrogen;             // Natrium
    private Double phosphorus;           // Fosfor
    private Double potassium;             // Kalium
}

