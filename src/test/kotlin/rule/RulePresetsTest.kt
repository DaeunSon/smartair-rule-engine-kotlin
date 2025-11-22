package rule

import com.daeun.smartair.domain.data.Pollutant
import com.daeun.smartair.rule.RapidRiseRule
import com.daeun.smartair.rule.RulePresets
import com.daeun.smartair.rule.ThresholdRule
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class RulePresetsTest : StringSpec({
    "defaultRules는 총 7개의 룰을 반환한다" {
        val rules = RulePresets.defaultRules()

        rules.size shouldBe 7
    }

    "TVOC에 대한 ThresholdRule이 포함되어 있다" {
        val rules = RulePresets.defaultRules()

        val tvocThresholdRule = rules.filterIsInstance<ThresholdRule>()
            .find { it.pollutant == Pollutant.TVOC }

        tvocThresholdRule.shouldNotBeNull()
        tvocThresholdRule.limit shouldBe RulePresets.Thresholds.TVOC
    }

    "TVOC에 대한 RapidRiseRule이 포함되어 있다" {
        val rules = RulePresets.defaultRules()

        val tvocRapidRiseRule = rules.filterIsInstance<RapidRiseRule>()
            .find { it.pollutant == Pollutant.TVOC }

        tvocRapidRiseRule.shouldNotBeNull()
        tvocRapidRiseRule.delta shouldBe RulePresets.RapidDeltas.TVOC
    }

    "eCO2에 대한 ThresholdRule이 포함되어 있다" {
        val rules = RulePresets.defaultRules()

        val eco2ThresholdRule = rules.filterIsInstance<ThresholdRule>()
            .find { it.pollutant == Pollutant.ECO2 }

        eco2ThresholdRule.shouldNotBeNull()
        eco2ThresholdRule.limit shouldBe RulePresets.Thresholds.ECO2
    }

    "eCO2에 대한 RapidRiseRule이 포함되어 있다" {
        val rules = RulePresets.defaultRules()

        val eco2RapidRiseRule = rules.filterIsInstance<RapidRiseRule>()
            .find { it.pollutant == Pollutant.ECO2 }

        eco2RapidRiseRule.shouldNotBeNull()
        eco2RapidRiseRule.delta shouldBe RulePresets.RapidDeltas.ECO2
    }

    "TEMPERATURE에 대한 RapidRiseRule이 포함되어 있다" {
        val rules = RulePresets.defaultRules()

        val temperatureRapidRiseRule = rules.filterIsInstance<RapidRiseRule>()
            .find { it.pollutant == Pollutant.TEMPERATURE }

        temperatureRapidRiseRule.shouldNotBeNull()
        temperatureRapidRiseRule.delta shouldBe RulePresets.RapidDeltas.TEMPERATURE
    }

    "HUMIDITY에 대한 RapidRiseRule이 포함되어 있다" {
        val rules = RulePresets.defaultRules()

        val humidityRapidRiseRule = rules.filterIsInstance<RapidRiseRule>()
            .find { it.pollutant == Pollutant.HUMIDITY }

        humidityRapidRiseRule.shouldNotBeNull()
        humidityRapidRiseRule.delta shouldBe RulePresets.RapidDeltas.HUMIDITY
    }

    "PRESSURE에 대한 RapidRiseRule이 포함되어 있다" {
        val rules = RulePresets.defaultRules()

        val pressureRapidRiseRule = rules.filterIsInstance<RapidRiseRule>()
            .find { it.pollutant == Pollutant.PRESSURE }

        pressureRapidRiseRule.shouldNotBeNull()
        pressureRapidRiseRule.delta shouldBe RulePresets.RapidDeltas.PRESSURE
    }

    "TEMPERATURE, HUMIDITY, PRESSURE에는 ThresholdRule이 포함되지 않는다" {
        val rules = RulePresets.defaultRules()

        val thresholdRules = rules.filterIsInstance<ThresholdRule>()
        val temperatureThreshold = thresholdRules.find { it.pollutant == Pollutant.TEMPERATURE }
        val humidityThreshold = thresholdRules.find { it.pollutant == Pollutant.HUMIDITY }
        val pressureThreshold = thresholdRules.find { it.pollutant == Pollutant.PRESSURE }

        temperatureThreshold shouldBe null
        humidityThreshold shouldBe null
        pressureThreshold shouldBe null
    }

    "ThresholdRule은 TVOC와 eCO2에 대해서만 존재한다" {
        val rules = RulePresets.defaultRules()

        val thresholdRules = rules.filterIsInstance<ThresholdRule>()

        thresholdRules.size shouldBe 2
        thresholdRules.map { it.pollutant } shouldContain Pollutant.TVOC
        thresholdRules.map { it.pollutant } shouldContain Pollutant.ECO2
    }

    "모든 룰이 올바른 타입이다" {
        val rules = RulePresets.defaultRules()

        val thresholdRules = rules.filterIsInstance<ThresholdRule>()
        val rapidRiseRules = rules.filterIsInstance<RapidRiseRule>()

        thresholdRules.size shouldBe 2
        rapidRiseRules.size shouldBe 5
    }
})