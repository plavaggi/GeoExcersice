package com.exercise.geo.repository;

import com.exercise.geo.model.entity.GeoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GeoDataRepository extends JpaRepository<GeoData, UUID> {
    @Query(
            value = "select distance, country from geo_data gd  group by country, distance order by distance desc limit 1",
            nativeQuery = true)
    String findMaxDistance();

    @Query(
            value = "select distance, country from geo_data gd  group by country, distance order by distance asc limit 1",
            nativeQuery = true)
    String findMinDistance();

    @Query(
            value = "select sum (distancia) distancia, sum (pais) pais from (select sum(distance) distancia,count(country) pais from public.geo_data group by country) as sumDisByCountry",
            nativeQuery = true)
    String averageDistanceAndCountry();

}
