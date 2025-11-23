package rule

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.data.Pollutant
import com.daeun.smartair.domain.report.AnomalyType
import com.daeun.smartair.rule.RuleEngine
import com.daeun.smartair.rule.RulePresets
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class RulePresetsIntegrationTest : StringSpec({
    "TVOC 임계치 초과와 급상승이 동시에 발생하면 두 가지 이상이 모두 감지된다" {
        val rules = RulePresets.defaultRules()
        val engine = RuleEngine(rules)

        val baseTime = LocalDateTime.now()
        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(5), 24.0, 40.0, 1013.0, 200.0, 500.0),
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.0, 40.0, 1013.0,
            avgTvoc = 700.0,  // 임계치 초과(600) + 급상승(400 > 300)
            avgEco2 = 500.0
        )

        val results = engine.evaluate(current, history)

        results.shouldHaveSize(2)
        
        val thresholdResult = results.find { 
            it.pollutant == Pollutant.TVOC && it.type == AnomalyType.THRESHOLD_EXCEEDED 
        }
        val rapidRiseResult = results.find { 
            it.pollutant == Pollutant.TVOC && it.type == AnomalyType.RAPID_RISE 
        }

        thresholdResult.shouldNotBeNull()
        thresholdResult.value shouldBe 700.0
        
        rapidRiseResult.shouldNotBeNull()
        rapidRiseResult.value shouldBe 400.0
    }

    "eCO2 임계치 초과와 급상승이 동시에 발생하면 두 가지 이상이 모두 감지된다" {
        val rules = RulePresets.defaultRules()
        val engine = RuleEngine(rules)

        val baseTime = LocalDateTime.now()
        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(5), 24.0, 40.0, 1013.0, 300.0, 500.0),
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 600.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.0, 40.0, 1013.0,
            avgTvoc = 300.0,
            avgEco2 = 1100.0  // 임계치 초과(1000) + 급상승(500 > 400)
        )

        val results = engine.evaluate(current, history)

        results.shouldHaveSize(2)
        
        val thresholdResult = results.find { 
            it.pollutant == Pollutant.ECO2 && it.type == AnomalyType.THRESHOLD_EXCEEDED 
        }
        val rapidRiseResult = results.find { 
            it.pollutant == Pollutant.ECO2 && it.type == AnomalyType.RAPID_RISE 
        }

        thresholdResult.shouldNotBeNull()
        thresholdResult.value shouldBe 1100.0
        
        rapidRiseResult.shouldNotBeNull()
        rapidRiseResult.value shouldBe 500.0
    }

    "여러 오염물질에서 동시에 이상이 감지된다" {
        val rules = RulePresets.defaultRules()
        val engine = RuleEngine(rules)

        val baseTime = LocalDateTime.now()
        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            30.0,  // 온도 급상승 (6.0 > 5.0)
            55.0,  // 습도 급상승 (15.0 > 15.0)
            1021.0,  // 기압 급상승 (8.0 > 8.0)
            700.0,  // TVOC 임계치 초과
            1100.0  // eCO2 임계치 초과
        )

        val results = engine.evaluate(current, history)

        // TVOC: 임계치 초과 + 급상승 (400 > 300)
        // eCO2: 임계치 초과 + 급상승 (600 > 400)
        // 온도: 급상승
        // 습도: 급상승
        // 기압: 급상승
        results.shouldHaveSize(7)

        results.map { it.pollutant to it.type }.shouldContain(Pollutant.TVOC to AnomalyType.THRESHOLD_EXCEEDED)
        results.map { it.pollutant to it.type }.shouldContain(Pollutant.TVOC to AnomalyType.RAPID_RISE)
        results.map { it.pollutant to it.type }.shouldContain(Pollutant.ECO2 to AnomalyType.THRESHOLD_EXCEEDED)
        results.map { it.pollutant to it.type }.shouldContain(Pollutant.ECO2 to AnomalyType.RAPID_RISE)
        results.map { it.pollutant to it.type }.shouldContain(Pollutant.TEMPERATURE to AnomalyType.RAPID_RISE)
        results.map { it.pollutant to it.type }.shouldContain(Pollutant.HUMIDITY to AnomalyType.RAPID_RISE)
        results.map { it.pollutant to it.type }.shouldContain(Pollutant.PRESSURE to AnomalyType.RAPID_RISE)
    }

    "정상 데이터에서는 이상이 감지되지 않는다" {
        val rules = RulePresets.defaultRules()
        val engine = RuleEngine(rules)

        val baseTime = LocalDateTime.now()
        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(5), 24.0, 40.0, 1013.0, 300.0, 500.0),
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 350.0, 600.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            24.5,  // 온도 변화 작음 (0.5 < 5.0)
            42.0,  // 습도 변화 작음 (2.0 < 15.0)
            1014.0,  // 기압 변화 작음 (1.0 < 8.0)
            400.0,  // TVOC 임계치 미만 (400 < 600), 변화 작음 (50 < 300)
            700.0  // eCO2 임계치 미만 (700 < 1000), 변화 작음 (100 < 400)
        )

        val results = engine.evaluate(current, history)

        results.shouldHaveSize(0)
    }

    "온도, 습도, 기압은 임계치 초과만으로는 이상이 감지되지 않는다" {
        val rules = RulePresets.defaultRules()
        val engine = RuleEngine(rules)

        val baseTime = LocalDateTime.now()
        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0)
        )

        val current = AirQualityData(
            "sensor-1", baseTime,
            27.0,  // 온도 높지만 급상승 아님 (3.0 < 5.0)
            50.0,  // 습도 높지만 급상승 아님 (10.0 < 15.0)
            1018.0,  // 기압 높지만 급상승 아님 (5.0 < 8.0)
            300.0,
            500.0
        )

        val results = engine.evaluate(current, history)

        // 온도, 습도, 기압은 ThresholdRule이 없으므로 임계치 초과만으로는 감지 안됨
        // 급상승도 아니므로 (변화량이 기준 미만)
        results.shouldHaveSize(0)
    }
})

