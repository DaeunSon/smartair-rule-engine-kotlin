package domain.data

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.data.Pollutant
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class AirQualityDataTest : StringSpec({
    "sensorId가 비어있으면 예외를 발생시킨다" {
        val exception = shouldThrow<IllegalArgumentException> {
            AirQualityData(
                sensorId = "",
                measuredAt = LocalDateTime.now(),
                avgTemperature = 24.0,
                avgHumidity = 40.0,
                avgPressure = 1013.0,
                avgTvoc = 300.0,
                avgEco2 = 500.0
            )
        }
        exception.message shouldBe "sensorId는 비어있을 수 없습니다."
    }

    "sensorId가 공백만 있으면 예외를 발생시킨다" {
        val exception = shouldThrow<IllegalArgumentException> {
            AirQualityData(
                sensorId = "   ",
                measuredAt = LocalDateTime.now(),
                avgTemperature = 24.0,
                avgHumidity = 40.0,
                avgPressure = 1013.0,
                avgTvoc = 300.0,
                avgEco2 = 500.0
            )
        }
        exception.message shouldBe "sensorId는 비어있을 수 없습니다."
    }

    "sensorId가 유효하면 정상적으로 생성된다" {
        val data = AirQualityData(
            sensorId = "sensor-1",
            measuredAt = LocalDateTime.now(),
            avgTemperature = 24.0,
            avgHumidity = 40.0,
            avgPressure = 1013.0,
            avgTvoc = 300.0,
            avgEco2 = 500.0
        )
        data.sensorId shouldBe "sensor-1"
    }

    "TEMPERATURE에 대해 diff를 올바르게 계산한다" {
        val baseTime = LocalDateTime.now()
        val current = AirQualityData(
            "sensor-1", baseTime,
            30.0, 40.0, 1013.0, 300.0, 500.0
        )
        val other = AirQualityData(
            "sensor-1", baseTime.minusMinutes(1),
            24.0, 40.0, 1013.0, 300.0, 500.0
        )

        val diff = current.diff(other, Pollutant.TEMPERATURE)

        diff shouldBe 6.0
    }

    "diff는 음수 값도 올바르게 계산한다" {
        val baseTime = LocalDateTime.now()
        val current = AirQualityData(
            "sensor-1", baseTime,
            20.0, 40.0, 1013.0, 300.0, 500.0
        )
        val other = AirQualityData(
            "sensor-1", baseTime.minusMinutes(1),
            24.0, 40.0, 1013.0, 300.0, 500.0
        )

        val diff = current.diff(other, Pollutant.TEMPERATURE)

        diff shouldBe -4.0
    }
})