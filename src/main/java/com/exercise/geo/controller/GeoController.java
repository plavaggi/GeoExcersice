package com.exercise.geo.controller;


import com.exercise.geo.dto.CountryDataDto;
import com.exercise.geo.service.GeoService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geo")
public class GeoController {

    @Autowired
    private GeoService geoService;

    @PostMapping("/test")
    public ResponseEntity<CountryDataDto> xeExchange(@RequestBody (required=true) JsonNode source) {
        String ip ="";
        if (source!=null) ip = source.get("ip").asText();
        CountryDataDto response = geoService.getDataByIp(ip);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
