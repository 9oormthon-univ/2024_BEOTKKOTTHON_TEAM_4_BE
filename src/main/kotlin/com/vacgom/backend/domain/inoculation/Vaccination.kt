package com.vacgom.backend.domain.inoculation

import com.vacgom.backend.domain.inoculation.constants.VaccinationType
import com.vacgom.backend.global.auditing.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*

@Entity
@Table(name = "t_vaccination")
class Vaccination(
        val diseaseName: String,
        val vaccineName: String,
        val minOrder: Long,
        val maxOrder: Long,
        @Enumerated(EnumType.STRING) val vaccinationType: VaccinationType
) : BaseEntity() {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "vaccination_id")
    val id: UUID? = null
}

