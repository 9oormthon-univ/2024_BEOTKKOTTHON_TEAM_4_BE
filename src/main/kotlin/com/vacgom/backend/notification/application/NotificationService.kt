package com.vacgom.backend.notification.application

import com.vacgom.backend.global.exception.error.BusinessException
import com.vacgom.backend.member.application.MemberService
import com.vacgom.backend.member.exception.MemberError
import com.vacgom.backend.notification.domain.Notification
import com.vacgom.backend.notification.domain.NotificationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val memberService: MemberService,
) {
    fun getAllNotifications(memberId: UUID): List<Notification> {
        return notificationRepository.findAllByMemberIdOrderByCreatedAt(memberId)
    }

    fun getAllUnreadNotifications(memberId: UUID): List<Notification> {
        return notificationRepository.findAllByMemberIdOrderByCreatedAt(memberId).filter { it.isRead == false }
    }

    fun setAsRead(notificationId: Long) {
        val notification =
            notificationRepository.findById(notificationId)
                .orElseThrow { throw BusinessException(MemberError.NOT_FOUND) }
        notification.isRead = true
        notificationRepository.save(notification)
    }

    fun setAllAsRead(memberId: UUID) {
        val notifications =
            notificationRepository.findAllByMemberIdOrderByCreatedAt(memberId).map {
                it.isRead = true
                it
            }
        notificationRepository.saveAll(notifications)
    }

    fun sendNotification(
        memberId: UUID,
        content: String,
        type: String,
    ): Notification {
        val member = memberService.findMember(memberId) ?: throw BusinessException(MemberError.NOT_FOUND)
        return notificationRepository.save(
            Notification(
                member = member,
                content = content,
                type = type,
                createdAt = Date(),
            ),
        )
    }
}
