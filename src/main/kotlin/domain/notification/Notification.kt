package com.daeun.smartair.domain.notification

import com.daeun.smartair.domain.report.AnomalyReport
import java.time.LocalDateTime

data class Notification (
    val id: Long? = null,
    val userId: Long?,
    val title: String,
    val message: String,
    val level: NotificationLevel,
    val read: Boolean = false,
    val createdAt: LocalDateTime,
    val relatedAnomalyId: Long? = null,
    val anomalySnapshot: AnomalyReport? = null
)