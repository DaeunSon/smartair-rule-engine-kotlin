package com.daeun.smartair.rule

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.report.AnomalyResult

sealed interface AnomalyRule {
    val name : String

    fun evaluate(
        current: AirQualityData,
        history: List<AirQualityData>
    ): AnomalyResult?
}