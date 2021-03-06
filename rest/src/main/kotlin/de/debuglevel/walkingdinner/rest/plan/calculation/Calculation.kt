package de.debuglevel.walkingdinner.rest.plan.calculation

import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.plan.Plan
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class Calculation(
    /**
     * UUID of the calculation
     */
    @Id
    @GeneratedValue
    val id: UUID? = null,
    /**
     * Whether the calculation of the plan has finished or is still in progress
     */
    var finished: Boolean,
    /**
     * Survey data as CSV to base the calculation on
     */
    @Lob
    val surveyfile: String,
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
    var end: LocalDateTime? = null
) {
    override fun toString(): String {
        return "Calculation(id=$id)"
    }
}
