package com.exercise.geo.service.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exercise.geo.dto.CountryDataDto;
import com.exercise.geo.response.Ip2CountryResponse;
import com.exercise.geo.response.RestCountryLanguage;
import com.exercise.geo.response.RestCountryResponse;
import com.exercise.geo.service.GeoService;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoServiceImpl implements GeoService {

	@Value("${external.endpoint.url.api.ip2Country}")
	private String ip2CountryEndpoint;

	@Value("${external.endpoint.url.api.restCountry}")
	private String restCountryEndpoint;

	@Value("${external.endpoint.url.api.currencyLayer}")
	private String currencyLayerEndpoint;

	@Override
	public CountryDataDto getDataByIp(String ip) {
		Ip2CountryResponse ip2Country = getCountryByIp(ip);
		String isoCode3 = ip2Country.getCountryCode3();

		RestCountryResponse countryData = getCountryDatAByIsoCode(isoCode3);
		String currency = countryData.getCurrencies().get(0).getCode();

		BigDecimal exchange = getExchange(currency);

		return prepareResponse(countryData, ip, isoCode3, currency, exchange);

	}

	private CountryDataDto prepareResponse(RestCountryResponse countryData, String ip, String isoCode3, String currency,
			BigDecimal exchange) {
		String name = countryData.getTranslations().get("es");
		List<String> horaLocal = new ArrayList<>();
		List<String> idiomas = new ArrayList<>();

		for (String usoHorario : countryData.getTimezones()) {
			OffsetDateTime now = OffsetDateTime.now(ZoneId.of(usoHorario));
			horaLocal.add(now.toString());
		}

		for (RestCountryLanguage language : countryData.getLanguages()) {
			idiomas.add(language.getName()+" ("+language.getIso639_1()+")");
		}

		String distance = distance(countryData.getLatlng().get(0), countryData.getLatlng().get(1));
		return new CountryDataDto(ip, name, isoCode3, horaLocal, idiomas, currency, distance);
	}

	private String distance(double lat2, double lon2) {
		if ((-34.0 == lat2) && (-64.0 == lon2)) {
			return String.valueOf(0);
		} else {
			double theta = -64.0 - lon2;
			double dist = Math.sin(Math.toRadians(-34.0)) * Math.sin(Math.toRadians(lat2))
					+ Math.cos(Math.toRadians(-34.0)) * Math.cos(Math.toRadians(lat2))
							* Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344;
			return (String.valueOf(dist));
		}
	}

	public Ip2CountryResponse getCountryByIp(String ip) {
		RestTemplate restTemplate = new RestTemplate();
		Ip2CountryResponse response = restTemplate.getForObject(ip2CountryEndpoint.concat(ip),
				Ip2CountryResponse.class);
		return response;
	}

	public RestCountryResponse getCountryDatAByIsoCode(String countryIsoCode) {
		RestTemplate restTemplate = new RestTemplate();
		RestCountryResponse response = restTemplate.getForObject(restCountryEndpoint.concat(countryIsoCode),
				RestCountryResponse.class);
		return response;

	}

	public BigDecimal getExchange(String currency) {
		RestTemplate restTemplate = new RestTemplate();
		JsonNode response = restTemplate.getForObject(currencyLayerEndpoint.concat(currency), JsonNode.class);
		return BigDecimal.valueOf(response.get("quotes").get("USD".concat(currency)).asDouble());
	}
}
