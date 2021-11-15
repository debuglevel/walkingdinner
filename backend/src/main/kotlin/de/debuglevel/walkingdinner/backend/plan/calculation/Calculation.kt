package de.debuglevel.walkingdinner.backend.plan.calculation

import de.debuglevel.walkingdinner.backend.participant.Team
import de.debuglevel.walkingdinner.backend.plan.Plan
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class Calculation(
    /**
     * UUID of the calculation
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID? = null,
    /**
     * Whether the calculation of the plan has finished or is still in progress
     */
    var finished: Boolean,
    /**
     * Size of the population (for calculation with Genetic Algorithm)
     */
    val populationsSize: Int,
    /**
     * Fitness level to beat (minimization problem, i.e. fitness must be less than this threshold) (for calculation with Genetic Algorithm)
     */
    val fitnessThreshold: Double,
    /**
     * Number of generations with constant fitness level to stop further calculations (for calculation with Genetic Algorithm)
     */
    val steadyFitness: Int,
    /**
     * The plan, once it is calculated
     */
    @OneToOne(cascade = [CascadeType.ALL])
    var plan: Plan?,
    /**
     * Teams to calculate into the plan
     */
    @OneToMany(cascade = [CascadeType.ALL])
    val teams: List<Team>,
    /**
     * ID on calculation microservices
     */
    var calculationId: UUID? = null,
    /**
     * When the calculation began
     */
    var begin: LocalDateTime? = null,
    /**
     * When the calculation finished
     */
    var end: LocalDateTime? = null,
    /**
     * Courses in the event
     * TODO: might be stored in a better way
     */
    @ElementCollection(fetch = FetchType.EAGER)
    val coursesNames: List<String>,
) {
    override fun toString(): String {
        return "Calculation(id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Calculation

        if (populationsSize != other.populationsSize) return false
        if (fitnessThreshold != other.fitnessThreshold) return false
        if (steadyFitness != other.steadyFitness) return false
        if (coursesNames != other.coursesNames) return false

        return true
    }

    override fun hashCode(): Int {
        var result = populationsSize
        result = 31 * result + fitnessThreshold.hashCode()
        result = 31 * result + steadyFitness
        result = 31 * result + coursesNames.hashCode()
        return result
    }
}
