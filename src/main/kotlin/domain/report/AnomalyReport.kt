package com.daeun.smartair.domain.report

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.data.Pollutant
import java.time.LocalDateTime

data class AnomalyReport (
    val id: Long? = null,
    val sensorId: String,
    val anomalyTimeStamp: LocalDateTime,
    val type: AnomalyType,
    val pollutant: Pollutant,
    val pollutantValue: Double,
    val description: String,
    val baseData: AirQualityData? = null,
    val relatedHourlySnapshotId: Long? = null,
    val relatedDailyReportId: Long? = null
)