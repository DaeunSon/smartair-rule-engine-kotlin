package rule

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.data.Pollutant
import com.daeun.smartair.domain.report.AnomalyType
import com.daeun.smartair.rule.RapidRiseRule
import com.daeun.smartair.rule.RuleEngine
import com.daeun.smartair.rule.ThresholdRule
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import java.time.LocalDateTime

class RuleEngineTest : StringSpec({
    "여러 규칙을 한 번에 평가할 수 있다" {
        val threshold = ThresholdRule(pollutant = Pollutant.TVOC, limit = 500.0)
        val rapidRise = RapidRiseRule(pollutant = Pollutant.TVOC, delta = 200.0)

        val engine = RuleEngine(listOf(threshold, rapidRise))

        val baseTime = LocalDateTime.now()
        val history = listOf(
            AirQualityData("sensor-1", baseTime.minusMinutes(1), 24.0, 40.0, 1013.0, 300.0, 500.0, 100.0, 100.0)
        )

        val current = AirQualityData("sensor-1", baseTime, 24.0, 40.0, 1013.0, 700.0, 500.0, 100.0, 100.0)

        val results = engine.evaluate(current, history)

        results.shouldHaveSize(2)
        assert(results.any {it.type == AnomalyType.THRESHOLD_EXCEEDED })
        assert(results.any { it.type == AnomalyType.RAPID_RISE })
    }
})