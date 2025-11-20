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
            AirQualityData("sensor-1", baseTime.minusMinutes(5), 24.0, 40.0, 1013.0, 200.0, 500.0, 100.0, 100.0),
            AirQualityData("sensor-1", baseTime.minusMinutes(3), 24.0, 40.0, 1013.0, 250.0, 500.0, 100.0, 100.0),
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0, 100.0, 100.0),
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.0, 40.0, 1013.0,
            avgTvoc = 700.0, // delta = 400
            avgEco2 = 500.0,
            avgRawh2 = 100.0,
            avgRawethanol = 100.0
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
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 450.0, 500.0, 100.0, 100.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.0, 40.0, 1013.0,
            avgTvoc = 700.0,   // delta = 250 < 300
            avgEco2 = 500.0,
            avgRawh2 = 100.0,
            avgRawethanol = 100.0
        )

        val result = rule.evaluate(current, history)

        result.shouldBeNull()
    }
}){

}