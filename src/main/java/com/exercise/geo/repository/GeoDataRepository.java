package com.exercise.geo.repository;

import com.exercise.geo.model.GeoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface GeoDataRepository extends JpaRepository<GeoData, UUID> {
    @Query(
            value = "select * from geo_data gd  order by distance desc limit 1",
            nativeQuery = true)
    GeoData findMaxDistance();

    @Query(
            value = "select * from geo_data gd  order by distance limit 1",
            nativeQuery = true)
    GeoData findMinDistance();

    @Query(
            value = "select sum (distancia) distancia, sum (pais) pais from (select sum(distance) distancia,count(country) pais from public.geo_data group by country) as sumDisByCountry",
            nativeQuery = true)
   List<String> sumTest();

}
