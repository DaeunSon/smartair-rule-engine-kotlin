package com.daeun.smartair.rule

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.data.Pollutant
import com.daeun.smartair.domain.report.AnomalyResult
import com.daeun.smartair.domain.report.AnomalyType

data class RapidRiseRule(
    override val name: String = "RAPID_RISE",
    val pollutant: Pollutant,
    val delta: Double
) : AnomalyRule {

    override fun evaluate(
        current: AirQualityData,
        history: List<AirQualityData>,
    ): AnomalyResult? {

        if (history.isEmpty()) return null

        val last = history.maxBy { it.measuredAt }
        val diff = current.diff(last, pollutant)

        if (diff >= delta) {
            return AnomalyResult(
                pollutant = pollutant,
                value = diff,
                type = AnomalyType.RAPID_RISE,
                message = "${pollutant.label} 값이 짧은 시간 동안 $diff 증가했습니다."
            )
        }

        return null;
    }
}