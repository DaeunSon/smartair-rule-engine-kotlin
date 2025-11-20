package com.daeun.smartair.domain.notification

import com.daeun.smartair.domain.report.AnomalyType

object NotificationLevelMapper {

    fun from(anomalyType: AnomalyType): NotificationLevel =
        when (anomalyType) {
            AnomalyType.THRESHOLD_EXCEEDED -> NotificationLevel.WARNING
            AnomalyType.RAPID_RISE -> NotificationLevel.DANGER
            AnomalyType.RAPID_DROP -> NotificationLevel.DANGER
            AnomalyType.SENSOR_ERROR -> NotificationLevel.INFO
        }
}