package com.daeun.smartair.domain.data

import java.time.LocalDateTime

data class AirQualityData(
    val sensorId: String,
    val measuredAt: LocalDateTime,
    val avgTemperature: Double,
    val avgHumidity: Double,
    val avgPressure: Double,
    val avgTvoc: Double,
    val avgEco2: Double,
) {
    init {
        require(sensorId.isNotBlank()) { "sensorId는 비어있을 수 없습니다." }
    }

    fun diff(other: AirQualityData, pollutant: Pollutant): Double {
        val currentValue = pollutant.getValue(this)
        val otherValue = pollutant.getValue(other)
        return currentValue - otherValue
    }
}