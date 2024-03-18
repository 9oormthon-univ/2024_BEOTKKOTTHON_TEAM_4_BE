package com.vacgom.backend.global.auditing

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseEntity {

    @Column(
            nullable = false,
            insertable = false,
            updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP"
    )
    @CreatedDate
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd a HH:mm"
    )
    open var createdDate: LocalDateTime? = null

    @Column(
            nullable = false,
            insertable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP"
    )
    @LastModifiedDate
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd a HH:mm"
    )
    open var updatedDate: LocalDateTime? = null
}
