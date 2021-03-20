package com.exercise.geo.service;

import com.exercise.geo.dto.GeoDataDto;
import com.exercise.geo.exception.ExchangeException;
import com.exercise.geo.exception.NotRecognizeIpException;

import java.util.HashMap;

public interface GeoService {

    GeoDataDto postDataByIp (String ip) throws NotRecognizeIpException, ExchangeException;

    GeoDataDto getMaxDistance();

    GeoDataDto getMinDistance();

    HashMap<String, Double> getAverageDistance();
}
