package com.exercise.geo.response;

import lombok.Data;

@Data
public class Ip2CountryResponse {

    private String countryCode;
    private String countryCode3;
    private String countryName;
    private String countryEmoji;

}
