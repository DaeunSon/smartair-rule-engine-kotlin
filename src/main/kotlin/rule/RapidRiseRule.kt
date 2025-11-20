package com.daeun.smartair.rule

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.data.Pollutant
import com.daeun.smartair.domain.report.AnomalyResult
import com.daeun.smartair.domain.report.AnomalyType

data class RapidRiseRule(
    override val name: String = "RAPID_RISE_${'$'}{pollutant.name}",
    val pollutant: Pollutant,
    val delta: Double
) : AnomalyRule {

    override fun evaluate(
        current: AirQualityData,
        history: List<AirQualityData>,
    ): AnomalyResult? {

        if (history.isEmpty()) return null;

        val last = history.maxBy { it.measuredAt }

        val currentValue = when (pollutant) {
            Pollutant.TVOC -> current.avgTvoc
            Pollutant.TEMPERATURE -> current.avgTemperature
            Pollutant.HUMIDITY -> current.avgHumidity
            Pollutant.PRESSURE -> current.avgPressure
            Pollutant.ECO2 -> current.avgEco2
            Pollutant.RAW_H2 -> current.avgRawh2
            Pollutant.RAW_ETHANOL -> current.avgRawethanol
            else -> return null
        }

        val lastValue = when (pollutant) {
            Pollutant.TVOC -> last.avgTvoc
            Pollutant.TEMPERATURE -> last.avgTemperature
            Pollutant.HUMIDITY -> last.avgHumidity
            Pollutant.PRESSURE -> last.avgPressure
            Pollutant.ECO2 -> last.avgEco2
            Pollutant.RAW_H2 -> last.avgRawh2
            Pollutant.RAW_ETHANOL -> last.avgRawethanol
            else -> return null
        }

        val diff = currentValue - lastValue
        if (diff < delta) return null

        return AnomalyResult(
            pollutant = pollutant,
            type = AnomalyType.RAPID_RISE,
            value = diff,
            message = "$pollutant 값이 최근 측정치 대비 $delta 이상 급격히 상승했습니다. (이전: $lastValue, 현재: $currentValue)"
        )
    }
}