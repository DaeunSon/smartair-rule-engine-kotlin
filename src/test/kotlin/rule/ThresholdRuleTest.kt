package rule

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.data.Pollutant
import com.daeun.smartair.domain.report.AnomalyType
import com.daeun.smartair.rule.ThresholdRule
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class ThresholdRuleTest : StringSpec({
    "TVOC가 임계값을 초과하면 이상치 결과를 생성한다"{
        val rule = ThresholdRule(
            pollutant = Pollutant.TVOC,
            limit = 500.0
        )

        val current = AirQualityData(
            sensorId = "1",
            measuredAt = LocalDateTime.now(),
            avgTemperature = 24.0,
            avgHumidity = 40.0,
            avgPressure = 1013.0,
            avgTvoc = 600.0, //임계값 초과
            avgEco2 = 500.0
        )

        val result = rule.evaluate(current, emptyList())

        result!!.pollutant shouldBe Pollutant.TVOC
        result.type shouldBe AnomalyType.THRESHOLD_EXCEEDED
        result.value shouldBe 600.0
    }

    "TVOC가 임계값 이하이면 이상치가 아니다" {
        val rule = ThresholdRule(
            pollutant = Pollutant.TVOC,
            limit = 500.0
        )

        val current = AirQualityData(
            sensorId = "sensor-1",
            measuredAt = LocalDateTime.now(),
            avgTemperature = 24.0,
            avgHumidity = 40.0,
            avgPressure = 1013.0,
            avgTvoc = 480.0,     // 임계값 이하
            avgEco2 = 500.0
        )

        val result = rule.evaluate(current, emptyList())

        result.shouldBeNull()
    }

    "TVOC가 임계값과 정확히 같으면 이상치가 아니다" {
        val rule = ThresholdRule(
            pollutant = Pollutant.TVOC,
            limit = 600.0
        )

        val current = AirQualityData(
            sensorId = "sensor-1",
            measuredAt = LocalDateTime.now(),
            avgTemperature = 24.0,
            avgHumidity = 40.0,
            avgPressure = 1013.0,
            avgTvoc = 600.0,
            avgEco2 = 800.0
        )

        val result = rule.evaluate(current, emptyList())

        result.shouldBeNull()
    }

    "eCO2가 임계값을 초과하면 이상치 결과를 생성한다" {
        val rule = ThresholdRule(
            pollutant = Pollutant.ECO2,
            limit = 1000.0
        )

        val current = AirQualityData(
            sensorId = "sensor-1",
            measuredAt = LocalDateTime.now(),
            avgTemperature = 24.0,
            avgHumidity = 40.0,
            avgPressure = 1013.0,
            avgTvoc = 300.0,
            avgEco2 = 1200.0  // 임계값 초과
        )

        val result = rule.evaluate(current, emptyList())

        result!!.pollutant shouldBe Pollutant.ECO2
        result.type shouldBe AnomalyType.THRESHOLD_EXCEEDED
        result.value shouldBe 1200.0
    }

    "eCO2가 임계값 이하이면 이상치가 아니다" {
        val rule = ThresholdRule(
            pollutant = Pollutant.ECO2,
            limit = 1000.0
        )

        val current = AirQualityData(
            sensorId = "sensor-1",
            measuredAt = LocalDateTime.now(),
            avgTemperature = 24.0,
            avgHumidity = 40.0,
            avgPressure = 1013.0,
            avgTvoc = 300.0,
            avgEco2 = 950.0  // 임계값 이하
        )

        val result = rule.evaluate(current, emptyList())

        result.shouldBeNull()
    }

    "eCO2가 임계값과 정확히 같으면 이상치가 아니다" {
        val rule = ThresholdRule(
            pollutant = Pollutant.ECO2,
            limit = 1000.0
        )

        val current = AirQualityData(
            sensorId = "sensor-1",
            measuredAt = LocalDateTime.now(),
            avgTemperature = 24.0,
            avgHumidity = 40.0,
            avgPressure = 1013.0,
            avgTvoc = 300.0,
            avgEco2 = 1000.0  // 임계값과 동일 (초과하지 않음)
        )

        val result = rule.evaluate(current, emptyList())

        result.shouldBeNull()
    }
})