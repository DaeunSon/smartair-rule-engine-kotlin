package com.daeun.smartair.domain.data

enum class Pollutant(
    val label: String,
    val valueExtractor: (AirQualityData) -> Double
) {
    TEMPERATURE(
        label = "Temperature",
        valueExtractor = { it.avgTemperature }
    ),

    HUMIDITY(
        label = "Humidity",
        valueExtractor = { it.avgHumidity }
    ),

    PRESSURE(
        label = "Pressure",
        valueExtractor = { it.avgPressure }
    ),

    TVOC(
        label = "TVOC",
        valueExtractor = { it.avgTvoc }
    ),

    ECO2(
        label = "eCO2",
        valueExtractor = { it.avgEco2 }
    ),

    RAW_H2(
        label = "Raw H2",
        valueExtractor = { it.avgRawh2 }
    ),

    RAW_ETHANOL(
        label = "Raw Ethanol",
        valueExtractor = { it.avgRawethanol }
    );

    /**
     * AirQualityData에서 해당 오염원의 값을 추출하는 공통 함수
     */
    fun getValue(data: AirQualityData): Double = valueExtractor(data)
}