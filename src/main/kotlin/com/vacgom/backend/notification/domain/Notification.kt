package com.vacgom.backend.notification.domain

import com.vacgom.backend.global.auditing.BaseEntity
import com.vacgom.backend.member.domain.Member
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "t_notification")
class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    val id: Long? = null,
    val content: String,
    val type: String,
    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,
    var isRead: Boolean = false,
    val createdAt: Date,
) : BaseEntity()
