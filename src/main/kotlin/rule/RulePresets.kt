package com.daeun.smartair.rule

import com.daeun.smartair.domain.data.Pollutant

object RulePresets {
    /**
     * 임계치 기준
     * TVOC, eCO2에 대해서만 임계치 초과 경고 룰을 적용한다.
     */
    object Thresholds {
        const val TVOC = 600.0
        const val ECO2 = 1000.0
    }

    /**
     * 급상승 기준
     * 단시간에 해당 값 이상으로 상승하면 이상 패턴으로 본다.
     * 온도/습도/기압/TVOC/eCO2 모두 체크 대상
     */
    object RapidDeltas{
        const val TEMPERATURE = 5.0    // °C
        const val HUMIDITY = 15.0      // %
        const val PRESSURE = 8.0       // hPa
        const val TVOC = 300.0         // ppb
        const val ECO2 = 400.0         // ppm
    }

    /**
     * 기본으로 사용할 룰 세트
     * TVOC / eCO2 : 임계치 + 급상승 모두 체크
     * 온도 / 습도 / 기압 : 급상승만 체크
     */
    fun defaultRules(): List<AnomalyRule> = listOf(
        //TVOC
        ThresholdRule(
            pollutant = Pollutant.TVOC,
            limit = Thresholds.TVOC
        ),
        RapidRiseRule(
            pollutant = Pollutant.TVOC,
            delta = RapidDeltas.TVOC
        ),

        //eCO2
        ThresholdRule(
            pollutant = Pollutant.ECO2,
            limit = Thresholds.ECO2
        ),
        RapidRiseRule(
            pollutant = Pollutant.ECO2,
            delta = RapidDeltas.ECO2
        ),

        //Temperature
        RapidRiseRule(
            pollutant = Pollutant.TEMPERATURE,
            delta = RapidDeltas.TEMPERATURE
        ),

        //Humidity
        RapidRiseRule(
            pollutant = Pollutant.HUMIDITY,
            delta = RapidDeltas.HUMIDITY
        ),

        //Pressure
        RapidRiseRule(
            pollutant = Pollutant.PRESSURE,
            delta = RapidDeltas.PRESSURE),
    )
}