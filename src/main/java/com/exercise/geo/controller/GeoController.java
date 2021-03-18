package com.exercise.geo.controller;


import com.exercise.geo.dto.GeoDataDto;
import com.exercise.geo.service.GeoService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/geo")
public class GeoController {

    @Autowired
    private GeoService geoService;

    @PostMapping("/test")
    public ResponseEntity<GeoDataDto> xeExchange(@RequestBody (required=true) JsonNode source) {
        String ip ="";
        if (source!=null) ip = source.get("ip").asText();
        GeoDataDto response = geoService.getDataByIp(ip);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/maxdistance")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<GeoDataDto> getMaxDistance()  {
        GeoDataDto data = geoService.getMaxDistance();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(path = "/mindistance")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<GeoDataDto> getMinDistance()  {
        GeoDataDto data = geoService.getMinDistance();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping(path = "/promedio")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Double> getAverageDistance()  {
    	Double data = geoService.getAverageDistance();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
