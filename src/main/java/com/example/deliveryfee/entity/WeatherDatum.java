package com.example.deliveryfee.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "weather_data")
public class WeatherDatum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 50)
    @NotNull
    @Column(name = "wmo_code", nullable = false, length = 50)
    private String wmoCode;

    @Column(name = "air_temperature", precision = 5, scale = 2)
    private BigDecimal airTemperature;

    @Column(name = "wind_speed", precision = 5, scale = 2)
    private BigDecimal windSpeed;

    @Size(max = 255)
    @Column(name = "phenomenon")
    private String phenomenon;

    @NotNull
    @Column(name = "observation_timestamp", nullable = false)
    private Instant observationTimestamp;

    public WeatherDatum() {
    }
    public WeatherDatum(Integer id, String name, String wmoCode, BigDecimal airTemperature, BigDecimal windSpeed, String phenomenon, Instant observationTimestamp) {
        this.id = id;
        this.name = name;
        this.wmoCode = wmoCode;
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
        this.phenomenon = phenomenon;
        this.observationTimestamp = observationTimestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWmoCode() {
        return wmoCode;
    }

    public void setWmoCode(String wmoCode) {
        this.wmoCode = wmoCode;
    }

    public BigDecimal getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(BigDecimal airTemperature) {
        this.airTemperature = airTemperature;
    }

    public BigDecimal getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(BigDecimal windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getPhenomenon() {
        return phenomenon;
    }

    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }

    public Instant getObservationTimestamp() {
        return observationTimestamp;
    }

    public void setObservationTimestamp(Instant observationTimestamp) {
        this.observationTimestamp = observationTimestamp;
    }

}