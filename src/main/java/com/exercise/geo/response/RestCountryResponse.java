package com.exercise.geo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestCountryResponse {

    private String name;
    private List<Double> latlng;
    private List<String> timezones;
    private List<RestCountryCurrency> currencies;
    private List<RestCountryLanguage> languages;
    private Map<String,String> translations;
}
