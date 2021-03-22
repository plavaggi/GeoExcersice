package com.exercise.geo.services;

import com.exercise.geo.dto.GeoDataDto;
import com.exercise.geo.repository.GeoDataRepository;
import com.exercise.geo.service.impl.GeoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeoServiceImplTest {
    @Mock
    GeoDataRepository geoDataRepository ;
    @Test
    public void getMaxDistanceTest(){
        when(geoDataRepository.findMaxDistance()).thenReturn("12000,España");
        GeoServiceImpl geoServiceImpl = new GeoServiceImpl(geoDataRepository);
        GeoDataDto result = geoServiceImpl.getMaxDistance();
        assertEquals(result.getDistanciaEstimada(), "12000");
        assertEquals(result.getPais(), "España");
    }
    @Test
    public void getMinDistanceTest(){
        when(geoDataRepository.findMinDistance()).thenReturn("12000,España");
        GeoServiceImpl geoServiceImpl = new GeoServiceImpl(geoDataRepository);
        GeoDataDto result = geoServiceImpl.getMinDistance();
        assertEquals(result.getDistanciaEstimada(), "12000");
        assertEquals(result.getPais(), "España");
    }
    @Test
    public void getAvgDistanceTest(){
        when(geoDataRepository.averageDistanceAndCountry()).thenReturn("92685.005702446,17");
        GeoServiceImpl geoServiceImpl = new GeoServiceImpl(geoDataRepository);
        HashMap<String, Double>  result = geoServiceImpl.getAverageDistance();
        assertEquals(result.get("distanciaPromedio"), 5452.059158967411);
    }

    //TODO complete test
}