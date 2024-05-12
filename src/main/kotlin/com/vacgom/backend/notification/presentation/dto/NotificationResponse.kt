package com.vacgom.backend.notification.presentation.dto

import com.vacgom.backend.notification.domain.Notification
import java.util.*

class NotificationResponse(
    val id: Long,
    val content: String,
    val type: String,
    val createdAt: Date,
) {
    companion object {
        fun of(notification: Notification): NotificationResponse {
            return NotificationResponse(
                id = notification.id!!,
                content = notification.content,
                type = notification.type,
                createdAt = notification.createdAt,
            )
        }
    }
}
