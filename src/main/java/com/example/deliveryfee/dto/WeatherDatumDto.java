package com.example.deliveryfee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDatumDto implements Serializable {
    @NotNull
    @Size(max = 255)
    @JacksonXmlProperty(localName = "name")
    private String name;

    @NotNull
    @Size(max = 50)
    @JacksonXmlProperty(localName = "wmocode")
    private String wmoCode;

    @JacksonXmlProperty(localName = "airtemperature")
    private BigDecimal airTemperature;

    @JacksonXmlProperty(localName = "windspeed")
    private BigDecimal windSpeed;

    @JacksonXmlProperty(localName = "phenomenon")
    @Size(max = 255)
    private String phenomenon;

    public WeatherDatumDto() {
        // No-arg constructor for Jackson
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getWmoCode() { return wmoCode; }
    public void setWmoCode(String wmoCode) { this.wmoCode = wmoCode; }

    public BigDecimal getAirTemperature() { return airTemperature; }
    public void setAirTemperature(BigDecimal airTemperature) { this.airTemperature = airTemperature; }

    public BigDecimal getWindSpeed() { return windSpeed; }
    public void setWindSpeed(BigDecimal windSpeed) { this.windSpeed = windSpeed; }

    public String getPhenomenon() { return phenomenon; }
    public void setPhenomenon(String phenomenon) { this.phenomenon = phenomenon; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherDatumDto entity = (WeatherDatumDto) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.wmoCode, entity.wmoCode) &&
                Objects.equals(this.airTemperature, entity.airTemperature) &&
                Objects.equals(this.windSpeed, entity.windSpeed) &&
                Objects.equals(this.phenomenon, entity.phenomenon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, wmoCode, airTemperature, windSpeed, phenomenon);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "wmoCode = " + wmoCode + ", " +
                "airTemperature = " + airTemperature + ", " +
                "windSpeed = " + windSpeed + ", " +
                "phenomenon = " + phenomenon + ")";
    }
}