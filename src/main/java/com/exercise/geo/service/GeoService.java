package com.exercise.geo.service;

import com.exercise.geo.dto.GeoDataDto;

public interface GeoService {

    GeoDataDto getDataByIp (String ip);

    GeoDataDto getMaxDistance();

    GeoDataDto getMinDistance();
}
