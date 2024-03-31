package com.example.deliveryfee.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@JacksonXmlRootElement(localName = "observations")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObservationsDto implements Serializable {

    @JacksonXmlProperty(isAttribute = true, localName = "timestamp")
    private Instant observationTimestamp;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "station")
    private List<WeatherDatumDto> stations;

    public ObservationsDto() {
    }

    public ObservationsDto(Instant observationTimestamp, List<WeatherDatumDto> stations) {
        this.observationTimestamp = observationTimestamp;
        this.stations = stations;
    }

    // Getters and Setters

    public Instant getObservationTimestamp() {
        return observationTimestamp;
    }

    public void setObservationTimestamp(Instant observationTimestamp) {
        this.observationTimestamp = observationTimestamp;
    }

    public List<WeatherDatumDto> getStations() {
        return stations;
    }

    public void setStations(List<WeatherDatumDto> stations) {
        this.stations = stations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObservationsDto entity = (ObservationsDto) o;
        return Objects.equals(this.observationTimestamp, entity.observationTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observationTimestamp);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "observationTimestamp = " + observationTimestamp + ")";
    }
}