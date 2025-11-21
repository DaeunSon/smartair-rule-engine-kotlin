package com.daeun.smartair

import com.daeun.smartair.domain.data.AirQualityData
import com.daeun.smartair.domain.notification.NotificationFactory
import com.daeun.smartair.domain.report.AnomalyReport
import com.daeun.smartair.io.ConsoleInput
import com.daeun.smartair.rule.RuleEngine
import com.daeun.smartair.rule.RulePresets
import java.time.LocalDateTime

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
fun main() {

    val input = ConsoleInput()

    println("=== SmartAir RuleEngine Console Demo ===")
    println("사용자가 입력한 공기질 데이터를 기반으로 이상치 감지 / 리포트 / 알림을 생성합니다.\n")

    // 1. 데이터 개수 입력
    val count = input.readIntInput("몇 개의 측정값을 입력하시겠습니까? (최소 2개 이상): ")

    if (count <= 1) {
        throw IllegalArgumentException("이상치 감지를 위해서는 최소 2개 이상의 측정값이 필요합니다. 입력값: $count")
    }

    // 2. count 만큼 측정값 입력받기
    val now = LocalDateTime.now()
    val measurements = mutableListOf<AirQualityData>()

    println("\n각 측정값은 최근 값일수록 나중에 입력된다고 가정합니다.")
    println("예: 1번째 = 과거 데이터, 마지막 = 가장 최근 데이터\n")

    for (i in 1..count) {
        println("[$i/$count] 측정값을 입력합니다.")

        val temperature = input.readDoubleInput("  - 온도(℃)를 입력해주세요 (예: 24.5): ")
        val humidity = input.readDoubleInput("  - 습도(%)를 입력해주세요 (예: 45.0): ")
        val pressure = input.readDoubleInput("  - 기압(hPa)을 입력해주세요 (예: 1013.0): ")
        val tvoc = input.readDoubleInput("  - TVOC 값을 입력해주세요 (예: 350.0): ")
        val eco2 = input.readDoubleInput("  - eCO₂ 값을 입력해주세요 (예: 800.0): ")

        val measuredAt = now.minusMinutes((count - i).toLong())

        val data = AirQualityData(
            sensorId = "sensor-1",
            measuredAt = measuredAt,
            avgTemperature = temperature,
            avgHumidity = humidity,
            avgPressure = pressure,
            avgTvoc = tvoc,
            avgEco2 = eco2
        )

        measurements += data
    }

    // 3. history / current 분리
    val history = measurements.dropLast(1)
    val current = measurements.last()

    println("\n입력한 측정값 요약:")
    measurements.forEachIndexed { idx, d ->
        println("  [$idx] time=${d.measuredAt}, " +
                "temp=${d.avgTemperature}, hum=${d.avgHumidity}, pres=${d.avgPressure}, " +
                "tvoc=${d.avgTvoc}, eco2=${d.avgEco2}")
    }

    // 4. 룰 엔진 적용
    val rules = RulePresets.defaultRules()

    val engine = RuleEngine(rules)

    // 5. 이상치 평가
    val results = engine.evaluate(current, history)

    println("\n[1] AnomalyResult 결과")
    if (results.isEmpty()) {
        println("  - 이상치가 감지되지 않았습니다.")
        return
    } else {
        results.forEach { println("  - $it") }
    }

    // 6. AnomalyResult → AnomalyReport 변환
    val reports: List<AnomalyReport> = results.map { result ->
        AnomalyReport(
            id = null,
            sensorId = current.sensorId,
            anomalyTimeStamp = current.measuredAt,
            pollutant = result.pollutant,
            pollutantValue = result.value,
            type = result.type,
            description = result.message,
            baseData = current
        )
    }

    println("\n[2] AnomalyReport 결과")
    reports.forEach { println("  - $it") }

    // 7. AnomalyReport -> Notification 생성
    val userId = 1L
    val notifications = reports.map { report ->
        NotificationFactory.from(report, userId)
    }

    println("\n[3] Notification 결과")
    notifications.forEach { noti ->
        println("  - level=${noti.level}, title=${noti.title}")
        println("  - message=${noti.message}")
    }
}