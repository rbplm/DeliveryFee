package com.example.deliveryfee.service;

import com.example.deliveryfee.dto.ObservationsDto;
import com.example.deliveryfee.dto.WeatherDatumDto;
import com.example.deliveryfee.entity.WeatherDatum;
import com.example.deliveryfee.repository.WeatherDataRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherDataService {

    private final WeatherDataRepository weatherDataRepository;
    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;


    public WeatherDataService(WeatherDataRepository weatherDataRepository, RestTemplateBuilder restTemplateBuilder, XmlMapper xmlMapper) {
        this.weatherDataRepository = weatherDataRepository;
        this.restTemplate = restTemplateBuilder.build();
        this.xmlMapper = xmlMapper;
    }

    public void fetchAndPersistWeatherData() {
        String weatherDataUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
        String response = restTemplate.getForObject(weatherDataUrl, String.class);
        try {
            ObservationsDto observationsDto = xmlMapper.readValue(response, ObservationsDto.class);
            List<WeatherDatum> weatherDataList = mapDtosToEntities(observationsDto);
            weatherDataRepository.saveAll(weatherDataList);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch and persist weather data");
        }
    }

    private List<WeatherDatum> mapDtosToEntities(ObservationsDto observationsDto) {

        List<WeatherDatum> weatherDataList = new ArrayList<>();
        for (WeatherDatumDto station : observationsDto.getStations()) {
            if (station.getWmoCode().equals("41025") || station.getWmoCode().equals("26038")
                    || station.getWmoCode().equals("26242") || station.getWmoCode().equals("26231")
                    || station.getWmoCode().equals("41803")){
            WeatherDatum weatherDatum = new WeatherDatum();
            weatherDatum.setObservationTimestamp(observationsDto.getObservationTimestamp());
            weatherDatum.setName(station.getName());
            weatherDatum.setWmoCode(station.getWmoCode());
            weatherDatum.setAirTemperature(station.getAirTemperature());
            weatherDatum.setWindSpeed(station.getWindSpeed());
            weatherDatum.setPhenomenon(station.getPhenomenon());
            weatherDataList.add(weatherDatum); }

        }
        return weatherDataList;
    }
}
