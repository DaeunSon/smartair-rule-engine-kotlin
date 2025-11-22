package com.daeun.smartair.domain.notification

import com.daeun.smartair.domain.report.AnomalyReport
import com.daeun.smartair.domain.report.AnomalyType
import java.time.LocalDateTime

object NotificationFactory {

    fun from(
        report: AnomalyReport,
        userId: Long,
        createdAt: LocalDateTime = LocalDateTime.now()
    ): Notification {
        val level = NotificationLevelMapper.from(report.type)

        val title = buildTitle(report.type)
        val message = buildMessage(report)

        return Notification(
            userId = userId,
            title = title,
            message = message,
            level = level,
            createdAt = createdAt,
            relatedAnomalyId = report.id,
            anomalySnapshot = report
        )
    }

    private fun buildTitle(type: AnomalyType): String =
        when (type){
            AnomalyType.THRESHOLD_EXCEEDED -> "공기질 임계치 초과 감지"
            AnomalyType.RAPID_RISE -> "공기질 급상승 이상 감지"
            AnomalyType.SENSOR_ERROR -> "센서 오류 감지"
        }

    private fun buildMessage(report: AnomalyReport): String {
        val base = "[${report.type.label}] ${report.pollutant} 이상이 감지되었습니다."
        val valuePart = report.pollutantValue?.let { " 측정값: $it" } ?: ""
        val descPart = if (report.description.isNotBlank()) " (${report.description})" else ""
        return base + valuePart + descPart
    }

}