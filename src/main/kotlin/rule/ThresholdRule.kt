package com.daeun.smartair.rule

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.data.Pollutant
import com.daeun.smartair.domain.report.AnomalyResult
import com.daeun.smartair.domain.report.AnomalyType

data class ThresholdRule (
    override val name : String = "THRESHOLD_${'$'}{pollutant.name}",
    val pollutant : Pollutant,
    val limit : Double
    ) : AnomalyRule {

    override fun evaluate(
        current: AirQualityData,
        history: List<AirQualityData>
    ): AnomalyResult? {
        val value = when (pollutant) {
            Pollutant.TVOC -> current.avgTvoc
            Pollutant.TEMPERATURE -> current.avgTemperature
            Pollutant.HUMIDITY -> current.avgHumidity
            Pollutant.PRESSURE -> current.avgPressure
            Pollutant.ECO2 -> current.avgEco2
            Pollutant.RAW_H2 -> current.avgRawh2
            Pollutant.RAW_ETHANOL -> current.avgRawethanol
        }

        if (value <= limit) return null

        return AnomalyResult(
            pollutant = pollutant,
            type = AnomalyType.THRESHOLD_EXCEEDED,
            value = value,
            message = "$pollutant 값이 임계치 $limit 를 초과했습니다. (현재: $value)"
        )
    }
}