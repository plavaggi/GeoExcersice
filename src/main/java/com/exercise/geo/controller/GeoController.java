package com.exercise.geo.controller;


import com.exercise.geo.dto.GeoDataDto;
import com.exercise.geo.exception.ExchangeException;
import com.exercise.geo.exception.NotRecognizeIpException;
import com.exercise.geo.service.GeoService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestController
@RequestMapping("/geo-api")
public class GeoController {

    public static final String MISSING_IP_DATA = "Empty country data for the requested ip";


    @Autowired
    private GeoService geoService;

    @PostMapping("/trace-ip")
    public ResponseEntity<GeoDataDto> postDataByIp(@RequestBody JsonNode request) {
        try {
            String ip ="";
            if (request!=null) ip = request.get("ip").asText();
            GeoDataDto response = geoService.postDataByIp(ip);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (NotRecognizeIpException | ExchangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MISSING_IP_DATA,e);
        }

    }

    @GetMapping(path = "/max-distance")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<GeoDataDto> getMaxDistance()  {
        GeoDataDto data = geoService.getMaxDistance();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(path = "/min-distance")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<GeoDataDto> getMinDistance()  {
        GeoDataDto data = geoService.getMinDistance();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    
    @GetMapping(path = "/average")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<HashMap<String, Double>> getAverageDistance()  {
        HashMap<String, Double> data = geoService.getAverageDistance();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
