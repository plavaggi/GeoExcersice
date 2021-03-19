package com.exercise.geo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "Ip", "Fecha Actual", "Pais", "ISO code", "Idioma", "Moneda Local", "Hora", "Distancia Estimada" })
@Data
@NoArgsConstructor
public class GeoDataDto {
    @JsonProperty("Ip")
    private String ip;
    @JsonProperty("Pais")
    private String pais;
    @JsonProperty("ISO code")
    private String isoCode;
    @JsonProperty("Hora")
    private List<String> hora;
    @JsonProperty("Idioma")
    private List<String> idioma;
    @JsonProperty("Moneda Local")
    private List<String> monedaLocal;
    @JsonProperty("Distancia Estimada")
    private String distanciaEstimada;
    @JsonProperty("Fecha Actual")
    private Timestamp fechaActual;

    public GeoDataDto(String ip, String pais, String isoCode, List<String> hora, List<String> idioma, List<String> monedaLocal, String distanciaEstimada) {
        this.ip = ip;
    	this.pais = pais;
        this.isoCode = isoCode;
        this.hora = hora;
        this.idioma = idioma;
        this.monedaLocal = monedaLocal;
        this.distanciaEstimada = distanciaEstimada;
        this.fechaActual = new Timestamp(System.currentTimeMillis());
    }
}
