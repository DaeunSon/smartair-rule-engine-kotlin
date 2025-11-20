package com.daeun.smartair.domain.report

import com.daeun.smartair.domain.data.Pollutant

data class AnomalyResult (
    val pollutant: Pollutant,
    val type: AnomalyType,
    val value: Double,
    val message: String
)