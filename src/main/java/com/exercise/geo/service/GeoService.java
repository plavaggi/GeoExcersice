package com.exercise.geo.service;

import com.exercise.geo.dto.CountryDataDto;

public interface GeoService {

    CountryDataDto getDataByIp (String ip);
}
