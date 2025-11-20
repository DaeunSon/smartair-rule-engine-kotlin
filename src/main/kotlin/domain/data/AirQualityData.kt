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
    val avgRawh2: Double,
    val avgRawethanol: Double
) {
    init {
        require(sensorId.isNotBlank()) { "sensorId는 비어있을 수 없습니다." }
    }
}