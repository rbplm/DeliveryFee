package com.example.deliveryfee;

import com.example.deliveryfee.entity.WeatherDatum;
import com.example.deliveryfee.repository.WeatherDataRepository;
import com.example.deliveryfee.service.FeeCalculationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DeliveryFeeApplicationTests {

    @Autowired
    private FeeCalculationService feeCalculationService;

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @BeforeEach
    public void setup() {

        weatherDataRepository.deleteAll();


        WeatherDatum tallinnWeather = new WeatherDatum(
                null,
                "Tallinn",
                "26038",
                BigDecimal.valueOf(-5),
                BigDecimal.valueOf(15),
                "snow",
                Instant.now()
        );
        weatherDataRepository.save(tallinnWeather);


        WeatherDatum parnuWeather = new WeatherDatum(
                null,
                "Pärnu",
                "41803",
                BigDecimal.valueOf(-8),
                BigDecimal.valueOf(21),
                "snow",
                Instant.now()
        );
        weatherDataRepository.save(parnuWeather);


        WeatherDatum tartuWeather = new WeatherDatum(
                null,
                "Tartu",
                "41025",
                BigDecimal.valueOf(-3),
                BigDecimal.valueOf(10),
                "thunder",
                Instant.now()
        );
        weatherDataRepository.save(tartuWeather);

    }
    @Test
    public void whenTallinnCarNormalConditions_thenReturnBaseFee() {
        int city = 26038;
        String vehicleType = "Car";
        double expectedFee = 4.0;

        String calculatedFee = feeCalculationService.calculateFeeBasedOnWeather(city, vehicleType);
        assertThat(calculatedFee).isEqualTo(String.valueOf(expectedFee));
    }

    @Test
    public void whenTallinnScooterColdAndSnowy_thenReturnIncreasedFee() {
        int city = 26038;
        String vehicleType = "Scooter";
        double expectedFee = 5.0; // Base fee + cold weather fee + snow fee

        String calculatedFee = feeCalculationService.calculateFeeBasedOnWeather(city, vehicleType);
        assertThat(calculatedFee).isEqualTo(String.valueOf(expectedFee));
    }

    @Test
    public void whenTallinnBikeSnow_thenReturnIncreasedFee() {
        int city = 26038;
        String vehicleType = "Bike";
        double expectedFee = 5.0; // Base fee + snow fee + temperature fee + wind speed fee

        String calculatedFee = feeCalculationService.calculateFeeBasedOnWeather(city, vehicleType);
        assertThat(calculatedFee).isEqualTo(String.valueOf(expectedFee));
    }

    @Test
    public void whenTartuCar_thenReturnBaseFee() {
        int city = 41025;
        String vehicleType = "Car";
        double expectedFee = 3.5;

        String calculatedFee = feeCalculationService.calculateFeeBasedOnWeather(city, vehicleType);
        assertThat(calculatedFee).isEqualTo(String.valueOf(expectedFee));
    }

    @Test
    public void whenTartuScooterThunder_thenReturnError() {
        int city = 41025;
        String vehicleType = "Scooter";
        String expectedFee = "Usage of selected vehicle type is forbidden";

        String calculatedFee = feeCalculationService.calculateFeeBasedOnWeather(city, vehicleType);
        assertThat(calculatedFee).isEqualTo(String.valueOf(expectedFee));
    }

    @Test
    public void whenTartuBikeThunder_thenReturnError() {
        int city = 41025;
        String vehicleType = "Bike";
        String expectedFee = "Usage of selected vehicle type is forbidden";

        String calculatedFee = feeCalculationService.calculateFeeBasedOnWeather(city, vehicleType);
        assertThat(calculatedFee).isEqualTo(String.valueOf(expectedFee));
    }

    @Test
    public void whenParnuCarSnow_thenReturnBaseFee() {
        int city = 41803;
        String vehicleType = "Car";
        double expectedFee = 3.0;

        String calculatedFee = feeCalculationService.calculateFeeBasedOnWeather(city, vehicleType);
        assertThat(calculatedFee).isEqualTo(String.valueOf(expectedFee));
    }

    @Test
    public void whenParnuScooterSnow_thenReturnIncreasedFee() {
        int city = 41803;
        String vehicleType = "Scooter";
        double expectedFee = 4; // Base fee + snow fee + temperature fee

        String calculatedFee = feeCalculationService.calculateFeeBasedOnWeather(city, vehicleType);
        assertThat(calculatedFee).isEqualTo(String.valueOf(expectedFee));
    }

    @Test
    public void whenParnuBikeHighWind_thenReturnError() {
        int city = 41803;
        String vehicleType = "Bike";
        String expectedFee = "Usage of selected vehicle type is forbidden"; // High wind speed

        String calculatedFee = feeCalculationService.calculateFeeBasedOnWeather(city, vehicleType);
        assertThat(calculatedFee).isEqualTo(expectedFee);
    }

}
