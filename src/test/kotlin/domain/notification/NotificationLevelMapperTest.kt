package domain.notification

import com.daeun.smartair.domain.notification.NotificationLevel
import com.daeun.smartair.domain.notification.NotificationLevelMapper
import com.daeun.smartair.domain.report.AnomalyType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*

class NotificationLevelMapperTest : StringSpec({
    "THRESHOLD_EXCEEDED는 WARNING 레벨로 매핑된다" {
        val level = NotificationLevelMapper.from(AnomalyType.THRESHOLD_EXCEEDED)

        level shouldBe NotificationLevel.WARNING
    }

    "RAPID_RISE는 DANGER 레벨로 매핑된다" {
        val level = NotificationLevelMapper.from(AnomalyType.RAPID_RISE)

        level shouldBe NotificationLevel.DANGER
    }

    "SENSOR_ERROR는 INFO 레벨로 매핑된다" {
        val level = NotificationLevelMapper.from(AnomalyType.SENSOR_ERROR)

        level shouldBe NotificationLevel.INFO
    }

    "모든 AnomalyType이 올바른 NotificationLevel로 매핑된다" {
        NotificationLevelMapper.from(AnomalyType.THRESHOLD_EXCEEDED) shouldBe NotificationLevel.WARNING
        NotificationLevelMapper.from(AnomalyType.RAPID_RISE) shouldBe NotificationLevel.DANGER
        NotificationLevelMapper.from(AnomalyType.SENSOR_ERROR) shouldBe NotificationLevel.INFO
    }
})