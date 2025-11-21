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
            Pollutant.ECO2 -> current.avgEco2
            // 다른 오염물질은 ThresholdRule에서 처리하지 않으므로 즉시 종료
            Pollutant.TEMPERATURE, Pollutant.HUMIDITY, Pollutant.PRESSURE -> return null
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