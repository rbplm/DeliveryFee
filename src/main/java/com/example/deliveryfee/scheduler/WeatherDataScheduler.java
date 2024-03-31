package com.example.deliveryfee.scheduler;

import com.example.deliveryfee.service.WeatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherDataScheduler {

    private final WeatherDataService weatherDataService;

    @Autowired
    public WeatherDataScheduler(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @Scheduled(cron = "${cron.expression}") // cron.expression in application.properties
    public void scheduledWeatherDataImport() {
        weatherDataService.fetchAndPersistWeatherData();
    }
}
