package com.exercise.geo.service.impl;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.exercise.geo.model.CountryDistance;
import com.exercise.geo.model.GeoData;
import com.exercise.geo.repository.GeoDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exercise.geo.dto.GeoDataDto;
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

	private final GeoDataRepository geoDataRepository;

	@Override
	public GeoDataDto getDataByIp(String ip) {
		Ip2CountryResponse ip2Country = getCountryByIp(ip);
		String isoCode3 = ip2Country.getCountryCode3();

		RestCountryResponse countryData = getCountryDatAByIsoCode3(isoCode3);
		String currency = countryData.getCurrencies().get(0).getCode();
		String country = countryData.getTranslations().get("es");
		Double distance = distance(countryData.getLatlng().get(0), countryData.getLatlng().get(1));

		Double exchange = getExchange(currency);

		GeoData data = new GeoData(ip, country, distance);
		geoDataRepository.save(data);

		return prepareResponse(countryData, ip, isoCode3, currency, exchange, country, distance.toString());

	}

	@Override
	public GeoDataDto getMaxDistance() {
		GeoData data = geoDataRepository.findMaxDistance();
		GeoDataDto response = new GeoDataDto();
		response.setIp(data.getIp());
		response.setDistanciaEstimada(data.getDistance().toString());
		response.setPais(data.getCountry());

		return response;
	}

	@Override
	public GeoDataDto getMinDistance() {
		GeoData data = geoDataRepository.findMinDistance();
		GeoDataDto response = new GeoDataDto();
		response.setIp(data.getIp());
		response.setDistanciaEstimada(data.getDistance().toString());
		response.setPais(data.getCountry());

		return response;
	}

	@Override
	public Double getAverageDistance() {
		List<String> data = geoDataRepository.sumTest();
		String[] parts = data.get(0).split(",");
		double part1 = Double.valueOf(parts[0])/Double.valueOf(parts[1]); 
		return part1;
	}

	private GeoDataDto prepareResponse(RestCountryResponse countryData, String ip, String isoCode3, String currency,
			Double exchange, String country, String distance) {
		List<String> horaLocal = new ArrayList<>();
		List<String> idiomas = new ArrayList<>();

		for (String usoHorario : countryData.getTimezones()) {
			OffsetDateTime now = OffsetDateTime.now(ZoneId.of(usoHorario));
			horaLocal.add(now.toString());
		}

		for (RestCountryLanguage language : countryData.getLanguages()) {
			idiomas.add(language.getName() + " (" + language.getIso639_1() + ")");
		}

		currency = currency + (" = ") + 1 / exchange + " (USD)";

		return new GeoDataDto(ip, country, isoCode3, horaLocal, idiomas, currency, distance);
	}

	private double distance(double lat2, double lon2) {
		if ((-34.0 == lat2) && (-64.0 == lon2)) {
			return 0;
		} else {
			double theta = -64.0 - lon2;
			double dist = Math.sin(Math.toRadians(-34.0)) * Math.sin(Math.toRadians(lat2))
					+ Math.cos(Math.toRadians(-34.0)) * Math.cos(Math.toRadians(lat2))
							* Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344;
			return (dist);
		}
	}

	public Ip2CountryResponse getCountryByIp(String ip) {
		RestTemplate restTemplate = new RestTemplate();
		Ip2CountryResponse response = restTemplate.getForObject(ip2CountryEndpoint.concat(ip),
				Ip2CountryResponse.class);
		return response;
	}

	public RestCountryResponse getCountryDatAByIsoCode3(String countryIsoCode) {
		RestTemplate restTemplate = new RestTemplate();
		RestCountryResponse response = restTemplate.getForObject(restCountryEndpoint.concat(countryIsoCode),
				RestCountryResponse.class);
		return response;

	}

	public Double getExchange(String currency) {
		RestTemplate restTemplate = new RestTemplate();
		JsonNode response = restTemplate.getForObject(currencyLayerEndpoint.concat(currency), JsonNode.class);
		return response.get("quotes").get("USD".concat(currency)).asDouble();
	}

}
