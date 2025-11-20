package com.daeun.smartair.rule

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.report.AnomalyResult

class RuleEngine (
    private val rules: List<AnomalyRule>
) {

    fun evaluate(
        current: AirQualityData,
        history: List<AirQualityData> = emptyList()
    ): List<AnomalyResult> {
        return rules.mapNotNull { rule ->
            rule.evaluate(current, history)
        }
    }
}