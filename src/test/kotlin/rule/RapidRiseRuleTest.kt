package rule

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.data.Pollutant
import com.daeun.smartair.domain.report.AnomalyType
import com.daeun.smartair.rule.RapidRiseRule
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class RapidRiseRuleTest : StringSpec({
    "최근 데이터에 비해 TVOC가 급상승하면 이상치로 판단한다" {
        val rule = RapidRiseRule(
            pollutant = Pollutant.TVOC,
            delta = 300.0
        )

        val baseTime = LocalDateTime.now()

        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(5), 24.0, 40.0, 1013.0, 200.0, 500.0),
            AirQualityData("sensor-1", baseTime.minusMinutes(3), 24.0, 40.0, 1013.0, 250.0, 500.0),
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0),
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.0, 40.0, 1013.0,
            avgTvoc = 700.0, // delta = 400
            avgEco2 = 500.0
        )

        val result = rule.evaluate(current, history)

        result!!.pollutant shouldBe Pollutant.TVOC
        result.type shouldBe AnomalyType.RAPID_RISE
    }

    "변화량이 임계치 미만이면 이상치가 아니다" {
        val rule = RapidRiseRule(
            pollutant = Pollutant.TVOC,
            delta = 300.0
        )

        val baseTime = LocalDateTime.now()

        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 450.0, 500.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.0, 40.0, 1013.0,
            avgTvoc = 700.0,   // delta = 250 < 300
            avgEco2 = 500.0
        )

        val result = rule.evaluate(current, history)

        result.shouldBeNull()
    }

    "최근 데이터에 비해 eCO2가 급상승하면 이상치로 판단한다" {
        val rule = RapidRiseRule(
            pollutant = Pollutant.ECO2,
            delta = 400.0
        )

        val baseTime = LocalDateTime.now()

        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.0, 40.0, 1013.0,
            avgTvoc = 300.0,
            avgEco2 = 950.0  // delta = 450 > 400
        )

        val result = rule.evaluate(current, history)

        result!!.pollutant shouldBe Pollutant.ECO2
        result.type shouldBe AnomalyType.RAPID_RISE
    }

    "eCO2 변화량이 임계치 미만이면 이상치가 아니다" {
        val rule = RapidRiseRule(
            pollutant = Pollutant.ECO2,
            delta = 400.0
        )

        val baseTime = LocalDateTime.now()

        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.0, 40.0, 1013.0,
            avgTvoc = 300.0,
            avgEco2 = 850.0  // delta = 350 < 400
        )

        val result = rule.evaluate(current, history)

        result.shouldBeNull()
    }

    "최근 데이터에 비해 온도가 급상승하면 이상치로 판단한다" {
        val rule = RapidRiseRule(
            pollutant = Pollutant.TEMPERATURE,
            delta = 5.0
        )

        val baseTime = LocalDateTime.now()

        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            avgTemperature = 30.0,  // delta = 6.0 > 5.0
            40.0, 1013.0, 300.0, 500.0
        )

        val result = rule.evaluate(current, history)

        result!!.pollutant shouldBe Pollutant.TEMPERATURE
        result.type shouldBe AnomalyType.RAPID_RISE
    }

    "온도 변화량이 임계치 미만이면 이상치가 아니다" {
        val rule = RapidRiseRule(
            pollutant = Pollutant.TEMPERATURE,
            delta = 5.0
        )

        val baseTime = LocalDateTime.now()

        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            avgTemperature = 28.0,  // delta = 4.0 < 5.0
            40.0, 1013.0, 300.0, 500.0
        )

        val result = rule.evaluate(current, history)

        result.shouldBeNull()
    }

    "최근 데이터에 비해 습도가 급상승하면 이상치로 판단한다" {
        val rule = RapidRiseRule(
            pollutant = Pollutant.HUMIDITY,
            delta = 15.0
        )

        val baseTime = LocalDateTime.now()

        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.0,
            avgHumidity = 57.0,  // delta = 17.0 > 15.0
            1013.0, 300.0, 500.0
        )

        val result = rule.evaluate(current, history)

        result!!.pollutant shouldBe Pollutant.HUMIDITY
        result.type shouldBe AnomalyType.RAPID_RISE
    }

    "습도 변화량이 임계치 미만이면 이상치가 아니다" {
        val rule = RapidRiseRule(
            pollutant = Pollutant.HUMIDITY,
            delta = 15.0
        )

        val baseTime = LocalDateTime.now()

        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.0,
            avgHumidity = 53.0,  // delta = 13.0 < 15.0
            1013.0, 300.0, 500.0
        )

        val result = rule.evaluate(current, history)

        result.shouldBeNull()
    }

    "최근 데이터에 비해 기압이 급상승하면 이상치로 판단한다" {
        val rule = RapidRiseRule(
            pollutant = Pollutant.PRESSURE,
            delta = 8.0
        )

        val baseTime = LocalDateTime.now()

        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.0, 40.0,
            avgPressure = 1022.0,  // delta = 9.0 > 8.0
            300.0, 500.0
        )

        val result = rule.evaluate(current, history)

        result!!.pollutant shouldBe Pollutant.PRESSURE
        result.type shouldBe AnomalyType.RAPID_RISE
    }

    "기압 변화량이 임계치 미만이면 이상치가 아니다" {
        val rule = RapidRiseRule(
            pollutant = Pollutant.PRESSURE,
            delta = 8.0
        )

        val baseTime = LocalDateTime.now()

        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.0, 40.0,
            avgPressure = 1019.0,  // delta = 6.0 < 8.0
            300.0, 500.0
        )

        val result = rule.evaluate(current, history)

        result.shouldBeNull()
    }
}){

}