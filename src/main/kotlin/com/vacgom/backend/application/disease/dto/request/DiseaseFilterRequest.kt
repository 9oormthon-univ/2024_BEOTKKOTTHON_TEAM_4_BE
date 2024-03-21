import com.vacgom.backend.domain.disease.AgeCondition
import com.vacgom.backend.domain.disease.HealthCondition

class DiseaseFilterRequest(
    val age: List<AgeCondition>,
    val condition: List<HealthCondition>,
)
