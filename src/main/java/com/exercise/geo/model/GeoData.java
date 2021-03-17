package com.exercise.geo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "geo_data")
public class GeoData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "internal_id")
    private UUID internalId;
    private String ip;
    private String country;
    private Double distance;
    @Column(name = "creation_on")
    private Timestamp createdOn;

    public GeoData(String ip, String country, Double distance) {
        this.ip = ip;
        this.country = country;
        this.distance = distance;
        this.createdOn = new Timestamp(System.currentTimeMillis());
    }

}
