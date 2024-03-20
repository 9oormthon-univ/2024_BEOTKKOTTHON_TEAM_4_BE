package com.vacgom.backend.domain.member

import com.fasterxml.jackson.annotation.JsonFormat
import com.vacgom.backend.domain.member.constants.Sex
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Embeddable
class MemberDetails {
    var name: String? = null

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd"
    )
    var birthday: LocalDate? = null

    @Enumerated(EnumType.STRING)
    var sex: Sex? = null

    fun updateMemberInfo(
            name: String,
            birthday: String,
            sex: String
    ) {
        this.name = name
        this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ISO_LOCAL_DATE)
        this.sex = Sex.valueOf(sex.uppercase())
    }
}
