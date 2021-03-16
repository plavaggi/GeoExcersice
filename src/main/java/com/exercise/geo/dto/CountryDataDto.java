package com.exercise.geo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
public class CountryDataDto {
    private String nombre;
    private String ISOcode;
    private List<String> horaLocal;
    private List<String> idioma;
    private String monedaLocal;
    private String distanciaEstimada;
    private Timestamp date;

    public CountryDataDto(String nombre, String ISOcode, List<String> horaLocal, List<String> idioma, String monedaLocal, String distanciaEstimada) {
        this.nombre = nombre;
        this.ISOcode = ISOcode;
        this.horaLocal = horaLocal;
        this.idioma = idioma;
        this.monedaLocal = monedaLocal;
        this.distanciaEstimada = distanciaEstimada;
        this.date = new Timestamp(System.currentTimeMillis());
    }
}
