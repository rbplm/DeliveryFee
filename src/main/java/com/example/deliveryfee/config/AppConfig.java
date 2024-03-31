package com.example.deliveryfee.config;

import com.example.deliveryfee.service.FeeCalculationService;
import com.example.deliveryfee.service.WeatherDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(WeatherDataService weatherDataService, FeeCalculationService feeCalculationService) {
        return args -> {
            weatherDataService.fetchAndPersistWeatherData();
        };
    }
}
