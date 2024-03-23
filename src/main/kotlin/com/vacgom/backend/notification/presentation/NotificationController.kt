package com.vacgom.backend.notification.presentation

import com.vacgom.backend.global.security.annotation.AuthId
import com.vacgom.backend.member.application.MemberService
import com.vacgom.backend.notification.application.NotificationService
import com.vacgom.backend.notification.presentation.dto.NotificationRequest
import com.vacgom.backend.notification.presentation.dto.NotificationResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/notifications")
class NotificationController(
    val memberService: MemberService,
    val notificationService: NotificationService,
) {
    @GetMapping("/")
    fun getAllNotifications(
        @AuthId id: UUID,
    ): List<NotificationResponse> {
        return notificationService.getAllNotifications(id).map {
            NotificationResponse.of(it)
        }
    }

    @PostMapping("/users/{userId}")
    fun sendNotification(
        @PathVariable("userId") userId: String,
        @RequestBody() body: NotificationRequest,
    ) {
        notificationService.sendNotification(UUID.fromString(userId), body.content, body.type)
    }

    @GetMapping("/unread")
    fun getUnreadNotifications(
        @AuthId id: UUID,
    ): List<NotificationResponse> {
        return notificationService.getAllUnreadNotifications(id).map {
            NotificationResponse.of(it)
        }
    }

    @PostMapping("/markAllAsRead")
    fun markAllAsRead(
        @AuthId id: UUID,
    ) {
        notificationService.setAllAsRead(id)
    }

    @PostMapping("/{notificationId}/markAsRead")
    fun markAsRead(
        @PathVariable("notificationId") notificationId: String,
    ) {
        notificationService.setAsRead(notificationId.toLong())
    }
}
