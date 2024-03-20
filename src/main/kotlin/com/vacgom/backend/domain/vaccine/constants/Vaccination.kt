package com.vacgom.backend.domain.vaccine.constants

import com.vacgom.backend.exception.vaccine.VaccineError
import com.vacgom.backend.global.exception.error.BusinessException

enum class Vaccination(
        val diseaseName: String,
        val vaccineName: String,
        val minOrder: Long,
        val maxOrder: Long
) {
    BCG("결핵", "BCG(피내용)", 1, 1),
    HEPB("B형간염", "HepB", 1, 3),
    DTAP("디프테리아·파상풍·백일해", "DTaP", 1, 5),
    TDAP("디프테리아·파상풍·백일해", "Tda", 6, 6),
    IPV("폴리오", "IPV", 1, 4),
    HIB("b형헤모필루스인플루엔자", "Hib", 1, 4),
    PCV("폐렴구균", "PCV", 1, 4),
    ROTAVIRAL("로타바이러스 감염증", "로타바이러스", 1, 3),
    MMR("홍역·유행성이하선염·풍진", "MMR", 1, 2),
    VAR("수두", "VAR", 1, 2),
    HEPA("A형간염", "HepA", 1, 2),
    IJEV("일본뇌염", "IJEV(불활성화 백신)", 1, 5),
    LJEV("일본뇌염", "LJEV(약독화 생백신)", 1, 2),
    HPV("사람유두종바이러스감염증", "HPV", 1, 3),
    IIV("인플루엔자", "IIV", 1, Long.MAX_VALUE),
    HPV9("사람유두종바이러스감염증", "HPV9(가다실9)", 1, 3),
    COVID19("코로나19", "COVID19", 1, 3),
    MCV4("수막구균", "MCV(4가)", 1, 1);

    companion object {
        fun getVaccinationByName(vaccineName: String): Vaccination {
            return entries.find { it.vaccineName == vaccineName }
                    ?: throw BusinessException(VaccineError.UNKNOWN_VACCINE_REQUESTED).also {
                        println("Requested vaccine with name '$vaccineName' is unknown.")
                    }
        }
    }
}
