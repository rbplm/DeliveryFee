package com.example.deliveryfee.service;

import com.example.deliveryfee.entity.WeatherDatum;
import com.example.deliveryfee.repository.WeatherDataRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
public class FeeCalculationService {

    private static final int TALLINN_WMOCODE = 26038;
    private static final int TARTU_WMOCODE = 41025;
    private static final int PARNU_WMOCODE = 41803;
    private static final double FORBIDDEN_VEHICLE = -1;

    private int temperature;
    private int windSpeed;
    private String phenomenon;

    private final WeatherDataRepository weatherDataRepository;


    public FeeCalculationService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    public String calculateFeeBasedOnWeather(int vmoCode, String vehicleType) {
        double fee = 0;
        Instant to = Instant.now();
        Instant from = to.minusSeconds(3600);

        tryToFindMatchFromDatabaseOrUseDefaults(vmoCode, to, from);

        double weatherFee = calculateWeatherFee(vehicleType, temperature, windSpeed, phenomenon);

        if (weatherFee == FORBIDDEN_VEHICLE) {
            return "Usage of selected vehicle type is forbidden";
        } else {
            fee += calculateWeatherFee(vehicleType, temperature, windSpeed, phenomenon);
        }

        fee += calculateLocationFee(vmoCode, vehicleType);

        return String.valueOf(fee);
    }

    private void tryToFindMatchFromDatabaseOrUseDefaults(int vmoCode, Instant to, Instant from) {
        List<WeatherDatum> results;
        try {
            results = weatherDataRepository.findByNameContainingAndObservationTimestampBetween(vmoCode, from, to);
            if (results.isEmpty()) {
                setDefaultWeatherConditions();
            } else {
                WeatherDatum latestWeatherDatum = results.get(results.size() - 1);
                extractWeatherConditions(latestWeatherDatum);
            }
        } catch (DataAccessException e) {
            setDefaultWeatherConditions();
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private void setDefaultWeatherConditions() {
        temperature = 0;
        windSpeed = 0;
        phenomenon = "";
    }

    private void extractWeatherConditions(WeatherDatum weatherDatum) {
        try {
            temperature = weatherDatum.getAirTemperature().intValue();
        }
        catch (NullPointerException e) {
            temperature = 0;
        }
        try {
            windSpeed = weatherDatum.getWindSpeed().intValue();
        }
        catch (NullPointerException e) {
            windSpeed = 0;
        }
        try {
            phenomenon = weatherDatum.getPhenomenon();
        }
        catch (NullPointerException e) {
            phenomenon = "";
        }
    }


    private double calculateWeatherFee(String vehicleType, int temperature, int windSpeed, String phenomenon) {
        double weatherResult = 0;
        if (vehicleType.equalsIgnoreCase("Scooter") || vehicleType.equalsIgnoreCase("Bike")) {

            if (vehicleType.equalsIgnoreCase("Bike")) {
                if (windSpeed >= 10 && windSpeed <= 20) {
                    weatherResult += 0.5;
                } else if (windSpeed > 20) {
                    return -1;
                }
            }
            if (phenomenon.toLowerCase().contains("snow") || phenomenon.toLowerCase().contains("sleet")) {
                weatherResult += 1;
            } else if (phenomenon.toLowerCase().contains("rain") || phenomenon.toLowerCase().contains("shower")) {
                weatherResult += 0.5;
            } else if (phenomenon.toLowerCase().contains("glaze") || phenomenon.toLowerCase().contains("hail")
                    || phenomenon.toLowerCase().contains("thunder")) {
                return -1;
            }

            if (temperature < -10) {
                weatherResult += 1;
            } else if (temperature < 0) {
                weatherResult += 0.5;
            }

        }
        return weatherResult;
    }

    private static double calculateLocationFee(int vmoCode, String vehicleType) {
        double locationResult = 0;
        switch (vmoCode) {
            case TALLINN_WMOCODE -> {
                if (vehicleType.equalsIgnoreCase("Bike")) {
                    locationResult += 3;
                } else if (vehicleType.equalsIgnoreCase("Scooter")) {
                    locationResult += 3.5;
                } else {
                    locationResult += 4;
                }
            }
            case TARTU_WMOCODE -> {
                if (vehicleType.equalsIgnoreCase("Bike")) {
                    locationResult += 2.5;
                } else if (vehicleType.equalsIgnoreCase("Scooter")) {
                    locationResult += 3;
                } else {
                    locationResult += 3.5;
                }
            }
            case PARNU_WMOCODE -> {
                if (vehicleType.equalsIgnoreCase("Bike")) {
                    locationResult += 2;
                } else if (vehicleType.equalsIgnoreCase("Scooter")) {
                    locationResult += 2.5;
                } else {
                    locationResult += 3;
                }
            }
        }
        return locationResult;
    }

}




