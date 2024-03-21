package com.vacgom.backend.infrastructure.inoculation.persistence

import com.vacgom.backend.domain.inoculation.Inoculation
import com.vacgom.backend.domain.inoculation.constants.VaccinationType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface InoculationRepository : JpaRepository<Inoculation, UUID> {

    @Query("select i " +
            "from Inoculation i " +
            "where i.member.id = :memberId " +
            "and i.vaccination.vaccinationType = :vaccinationType")
    fun findInoculationsByMemberIdAndVaccinationType(memberId: UUID, vaccinationType: VaccinationType): List<Inoculation>

    @Query("select i " +
            "from Inoculation i " +
            "where i.member.id = :memberId and i.vaccination.diseaseName = :diseaseName " +
            "and i.vaccination.vaccinationType = :vaccinationType " +
            "order by i.date desc")
    fun findInoculationsByMemberIdAndVaccinationTypeAndDiseaseName(memberId: UUID, vaccinationType: VaccinationType, diseaseName: String): List<Inoculation>?
}
