package com.example.deliveryfee.repository;

import com.example.deliveryfee.entity.WeatherDatum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

import java.util.List;

public interface WeatherDataRepository extends JpaRepository<WeatherDatum, Integer> {


    @Query("SELECT w FROM WeatherDatum w WHERE w.wmoCode = :wmo AND w.observationTimestamp BETWEEN :start AND :end")
    List<WeatherDatum> findByNameContainingAndObservationTimestampBetween(@Param("wmo") int wmo, @Param("start") Instant start, @Param("end") Instant end);


}
