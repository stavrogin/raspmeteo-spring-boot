package it.flavio.springtest.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.flavio.springrest.database.table.WeatherData;
import it.flavio.springrest.mybatis.mapper.WeatherDataMapper;
import it.flavio.springtest.dto.WeatherDataDTO;

public class WeatherDataDAOImpl implements WeatherDataDAO {
	
	private static final String H2_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private WeatherDataMapper weatherDataMapper;
	
	/**
	 * Get all stored weather data
	 * @return the all stored weather data
	 */
	@Override
	public List<WeatherDataDTO> findAllWeatherData() {
		List<WeatherData> weatherDataList = weatherDataMapper.findAllWeatherData();
		List<WeatherDataDTO> weatherDataDTOList = new ArrayList<>();
		weatherDataList.forEach(d -> weatherDataDTOList.add(buildWeatherDataDTO(d)));
		return weatherDataDTOList;
	}
	
	@Override
	public List<WeatherDataDTO> findWeatherData(long daysBack) {
		LocalDateTime now = LocalDateTime.now().minusDays(daysBack).truncatedTo(ChronoUnit.DAYS);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(H2_TIMESTAMP_FORMAT);
		String dateString = now.format(formatter);
		List<WeatherData> weatherDataList = weatherDataMapper.findAllWeatherDataFromDate(dateString);
		List<WeatherDataDTO> weatherDataDTOList = new ArrayList<>();
		weatherDataList.forEach(d -> weatherDataDTOList.add(buildWeatherDataDTO(d)));
		return weatherDataDTOList;
	}
	
	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.DAYS);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(H2_TIMESTAMP_FORMAT);
		String dateString = now.format(formatter);
		System.out.println(dateString);
	}
	
	@Override
	public WeatherDataDTO insertWeatherData(WeatherDataDTO weatherDataDTO) {
		WeatherData weatherData = buildWeatherData(weatherDataDTO);
		weatherDataMapper.insertWeatherData(weatherData);
		weatherDataDTO = buildWeatherDataDTO(weatherData);
		return weatherDataDTO;
	}
	
	@Override
	public boolean deleteWeatherData(Long weatherdataId) {
		weatherDataMapper.deleteWeatherData(weatherdataId);
		return true;
	}
	
	
	/**
	 * Builds DTO from weather data DB model
	 * @param weatherData the weather data model
	 * @return the weather data dto 
	 */
	private WeatherDataDTO buildWeatherDataDTO(WeatherData weatherData) {
		WeatherDataDTO dto = new WeatherDataDTO.WeatherDataDTOBuilder()
				.weatherdataId(weatherData.getWeatherdataId())
				.ts(weatherData.getTs())
				.pressure(weatherData.getPressure())
				.temperature(weatherData.getTemperature())
				.altitude(weatherData.getAltitude())
				.description(weatherData.getDescription())
				.datasourceId(weatherData.getDatasourceId())
				.build();
		return dto;
	}
	
	/**
	 * Builds data DB model from DTO
	 * @param weatherDataDTO the DTO
	 * @return the weather data 
	 */
	private WeatherData buildWeatherData(WeatherDataDTO dto) {
		WeatherData weatherData = new WeatherData();
		weatherData.setWeatherdataId(dto.getWeatherdataId());
		weatherData.setTs(dto.getTs());
		weatherData.setPressure(dto.getPressure());
		weatherData.setTemperature(dto.getTemperature());
		weatherData.setAltitude(dto.getAltitude());
		weatherData.setDescription(dto.getDescription());
		weatherData.setDatasourceId(dto.getDatasourceId());
		return weatherData;
	}

}
