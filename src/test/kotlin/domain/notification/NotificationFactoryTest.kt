package domain.notification

import com.daeun.smartair.domain.data.Pollutant
import com.daeun.smartair.domain.notification.NotificationFactory
import com.daeun.smartair.domain.notification.NotificationLevel
import com.daeun.smartair.domain.report.AnomalyReport
import com.daeun.smartair.domain.report.AnomalyType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

class NotificationFactoryTest : StringSpec({
    "THRESHOLD_EXCEEDED 리포트는 WARNING 알림을 생성한다" {
        val report = AnomalyReport(
            id = 1L,
            sensorId = "sensor-1",
            anomalyTimeStamp = LocalDateTime.parse("2025-05-13T15:45:00"),
            pollutant = Pollutant.TVOC,
            pollutantValue = 650.0,
            type = AnomalyType.THRESHOLD_EXCEEDED,
            description = "TVOC가 권장 기준을 초과했습니다."
        )

        val userId = 100L
        val createdAt = LocalDateTime.parse("2025-05-13T15:45:00")


        val notification = NotificationFactory.from(report, userId, createdAt)

        notification.userId shouldBe userId
        notification.level shouldBe NotificationLevel.WARNING
        notification.title shouldBe "공기질 임계치 초과 감지"
        notification.relatedAnomalyId shouldBe 1L
        notification.anomalySnapshot shouldBe report
        notification.createdAt shouldBe createdAt
    }

    "RAPID_RISE 리포트는 DANGER 레벨 알림을 생성한다" {
        val report = AnomalyReport(
            id = 2L,
            sensorId = "sensor-1",
            anomalyTimeStamp = LocalDateTime.parse("2025-05-13T16:00:00"),
            pollutant = Pollutant.TVOC,
            pollutantValue = 900.0,
            type = AnomalyType.RAPID_RISE,
            description = "5분 내 TVOC가 급상승했습니다."
        )

        val notification = NotificationFactory.from(report, userId = 101L)

        notification.level shouldBe NotificationLevel.DANGER
    }
})