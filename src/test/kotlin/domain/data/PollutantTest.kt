package domain.data

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.data.Pollutant
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class PollutantTest : StringSpec({
    "TEMPERATURE의 getValue는 avgTemperature를 반환한다" {
        val data = AirQualityData(
            "sensor-1", LocalDateTime.now(),
            25.5, 40.0, 1013.0, 300.0, 500.0
        )

        val value = Pollutant.TEMPERATURE.getValue(data)

        value shouldBe 25.5
    }

    "모든 Pollutant의 label이 올바르게 설정되어 있다" {
        Pollutant.TEMPERATURE.label shouldBe "Temperature"
        Pollutant.HUMIDITY.label shouldBe "Humidity"
        Pollutant.PRESSURE.label shouldBe "Pressure"
        Pollutant.TVOC.label shouldBe "TVOC"
        Pollutant.ECO2.label shouldBe "eCO2"
    }
})