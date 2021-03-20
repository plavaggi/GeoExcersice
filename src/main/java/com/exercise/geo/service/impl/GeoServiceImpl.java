package com.exercise.geo.service.impl;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.exercise.geo.exception.BadRequestIpSearch;
import com.exercise.geo.exception.ExchangeException;
import com.exercise.geo.exception.NotRecognizeIpException;
import com.exercise.geo.model.entity.GeoData;
import com.exercise.geo.repository.GeoDataRepository;
import com.exercise.geo.model.restCountry.RestCountryCurrency;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exercise.geo.dto.GeoDataDto;
import com.exercise.geo.dto.Ip2CountryDto;
import com.exercise.geo.model.restCountry.RestCountryLanguage;
import com.exercise.geo.dto.RestCountryDto;
import com.exercise.geo.service.GeoService;

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
	public GeoDataDto postDataByIp(String ip) throws NotRecognizeIpException, ExchangeException {
		Ip2CountryDto ip2Country = getCountryByIp(ip);
		String isoCode3 = ip2Country.getCountryCode3();

		if(isoCode3.isEmpty()){
			throw new NotRecognizeIpException("400","The requested IP brings no location data, please try another one");
		}

		RestCountryDto countryData = getCountryDatAByIsoCode3(isoCode3);

		String country = countryData.getTranslations().get("es");
		Double distance = distance(countryData.getLatlng().get(0), countryData.getLatlng().get(1));

		List<String> exchange = getExchange(countryData.getCurrencies());

		GeoData data = new GeoData(ip, country, distance);
		geoDataRepository.save(data);

		return prepareResponse(countryData, ip, isoCode3, country, exchange, distance.toString());

	}

	@Override
	public GeoDataDto getMaxDistance() {
		String data = geoDataRepository.findMaxDistance();
		GeoDataDto response = new GeoDataDto();
		String[] parts = data.split(",");
		String distance = parts[0];
		String country = parts[1];

		response.setDistanciaEstimada(distance);
		response.setPais(country);

		return response;
	}

	@Override
	public GeoDataDto getMinDistance() {
		String data = geoDataRepository.findMinDistance();
		GeoDataDto response = new GeoDataDto();
		String[] parts = data.split(",");
		String distance = parts[0];
		String country = parts[1];

		response.setDistanciaEstimada(distance);
		response.setPais(country);

		return response;
	}

	@Override
	public HashMap<String, Double> getAverageDistance() {
		HashMap<String, Double> response = new HashMap<>();
		String data = geoDataRepository.averageDistanceAndCountry();
		String[] parts = data.split(",");
		Double distanciaPromedio = Double.valueOf(parts[0])/Double.valueOf(parts[1]);
		response.put("distanciaPromedio", distanciaPromedio);
		return response;
	}

	private GeoDataDto prepareResponse(RestCountryDto countryData, String ip, String isoCode3, String country, List<String> exchange, String distance) {
		List<String> horaLocal = new ArrayList<>();
		List<String> idiomas = new ArrayList<>();

		for (String usoHorario : countryData.getTimezones()) {
			OffsetDateTime now = OffsetDateTime.now(ZoneId.of(usoHorario));
			horaLocal.add(now.toString());
		}

		for (RestCountryLanguage language : countryData.getLanguages()) {
			idiomas.add(language.getName() + " (" + language.getIso639_1() + ")");
		}

		return new GeoDataDto(ip, country, isoCode3, horaLocal, idiomas, exchange, distance);
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

	public Ip2CountryDto getCountryByIp(String ip) {
		RestTemplate restTemplate = new RestTemplate();
		Ip2CountryDto response = new Ip2CountryDto();
		try {
			response = restTemplate.getForObject(ip2CountryEndpoint.concat(ip), Ip2CountryDto.class);
		}catch (RuntimeException e){
			throw new BadRequestIpSearch("400", "The ip must be a valid one (BAD REQUEST)");
		}

		return response;
	}

	public RestCountryDto getCountryDatAByIsoCode3(String countryIsoCode) {
		RestTemplate restTemplate = new RestTemplate();
		RestCountryDto response = restTemplate.getForObject(restCountryEndpoint.concat(countryIsoCode),
				RestCountryDto.class);
		return response;

	}

	public List<String> getExchange(List<RestCountryCurrency> currency) throws ExchangeException {

		String currencies = "";
		List<String> response= new ArrayList<>();

		for (RestCountryCurrency currencyCode: currency) {
			currencies = currencies + currencyCode.getCode() + ",";
		}
		currencies = currencies.substring(0, currencies.length() - 1);

		RestTemplate restTemplate = new RestTemplate();
		JsonNode exhcangeResponse = restTemplate.getForObject(currencyLayerEndpoint.concat(currencies), JsonNode.class);
		if(exhcangeResponse.isNull() || !exhcangeResponse.get("success").asBoolean()){
			throw new ExchangeException("403", "credentials error");
		}
		for (RestCountryCurrency currencyCode: currency) {
			response.add(currencyCode.getCode() + " = " + 1 / exhcangeResponse.get("quotes").get("USD"+currencyCode.getCode()).asDouble()+ " (USD)");
		}

		return response;
	}

}
