package com.exercise.geo.service.impl;

import com.exercise.geo.dto.CountryDataDto;
import com.exercise.geo.response.Ip2CountryResponse;
import com.exercise.geo.response.RestCountryResponse;
import com.exercise.geo.service.GeoService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoServiceImpl implements GeoService {

    @Value("${external.endpoint.url.api.ip2Country}")
    private String ip2CountryEndpoint;

    @Value("${external.endpoint.url.api.restCountry}")
    private String restCountry;

    @Value("${external.endpoint.url.api.currencyLayer}")
    private String currencyLayer;



    @Override
    public CountryDataDto getDataByIp(String ip) {
        Ip2CountryResponse ip2Country = getCountryByIp(ip);
        RestCountryResponse countryData = getCountryDatAByIsoCode(ip2Country.getCountryCode3());
        JsonNode getExchange = getExchange(countryData.getCurrencies().get(0).getCode());

        CountryDataDto countryDataDto = new CountryDataDto();
/*
        String nombre, String ISOcode, List<String> horaLocal, List<String> idioma, String monedaLocal, String distanciaEstimada
*/

        return countryDataDto;
    }

    public Ip2CountryResponse getCountryByIp (String ip) {
        RestTemplate restTemplate = new RestTemplate();
        Ip2CountryResponse ip2Country = restTemplate.getForObject(ip2CountryEndpoint.concat(ip), Ip2CountryResponse.class);
        return ip2Country;
    }

    public RestCountryResponse getCountryDatAByIsoCode (String countryIsoCode) {
        RestTemplate restTemplate = new RestTemplate();
        RestCountryResponse response = restTemplate.getForObject(restCountry.concat(countryIsoCode), RestCountryResponse.class);
        return response;
    }

    public JsonNode getExchange (String currency) {
        RestTemplate restTemplate = new RestTemplate();
        JsonNode response = restTemplate.getForObject(currencyLayer.concat(currency), JsonNode.class);
        return response;
    }
}
