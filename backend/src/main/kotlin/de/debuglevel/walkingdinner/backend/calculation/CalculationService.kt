package de.debuglevel.walkingdinner.backend.calculation

import de.debuglevel.walkingdinner.backend.calculation.client.CalculationClient
import de.debuglevel.walkingdinner.backend.calculation.client.CalculationRequest
import de.debuglevel.walkingdinner.backend.calculation.client.TeamRequest
import de.debuglevel.walkingdinner.backend.course.Courses
import de.debuglevel.walkingdinner.backend.plan.Plan
import de.debuglevel.walkingdinner.backend.plan.PlanService
import de.debuglevel.walkingdinner.backend.plan.client.PlanClient
import de.debuglevel.walkingdinner.backend.team.Team
import de.debuglevel.walkingdinner.backend.team.TeamService
import de.debuglevel.walkingdinner.backend.team.importer.csv.CsvTeamsImporter
import io.micronaut.http.client.exceptions.HttpClientResponseException
import jakarta.inject.Singleton
import mu.KotlinLogging
import java.util.*
import javax.transaction.Transactional

@Singleton
open class CalculationService(
    private val calculationClient: CalculationClient,
    private val planClient: PlanClient,
    private val planService: PlanService,
    private val teamService: TeamService,
    private val csvTeamsImporter: CsvTeamsImporter,
    private val calculationRepository: CalculationRepository
) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    open fun get(id: UUID): Calculation {
        logger.debug { "Getting calculation '$id'..." }
        val calculation = calculationRepository.findById(id).orElseThrow { CalculationNotFoundException(id) }

        // if calculation has no plan yet, try to fetch it from the microservice
        if (calculation.plan == null) {
            logger.debug { "Calculation ${calculation.id} has no plan yet" }
            updateFromMicroservice(calculation)
        }

        logger.debug { "Got calculation: $calculation" }
        return calculation
    }

    private fun updateFromMicroservice(calculation: Calculation) {
        logger.debug { "Fetching calculation (our id: ${calculation.id}, their id: ${calculation.calculationId}) from service..." }
        try {
            val calculationResponse = calculationClient.getOne(calculation.calculationId!!)
            logger.debug { "Received CalculationResponse: $calculationResponse..." }

            calculation.finished = calculationResponse.finished
            calculation.begin = calculationResponse.begin
            calculation.end = calculationResponse.end
            val planId = calculationResponse.planId

            if (calculation.finished) {
                val savedPlan = fetchPlan(calculation, planId)
                calculation.plan = savedPlan // TODO: not sure if this gets persisted somehow
            }
        } catch (e: HttpClientResponseException) {
            logger.error { "Service returned ${e.status} (${e.message})" }
        }
    }

    private fun fetchPlan(
        calculation: Calculation,
        planId: UUID?
    ): Plan {
        logger.debug { "Calculation ${calculation.id} is finished on calculation microservice; fetching its plan ${planId!!} from microservice..." }
        val planResponse = planClient.getOne(planId!!)

        val fetchedTeams = calculation.teams.map { teamService.get(it.id!!) }

        val plan = Plan(
            //meetings = planResponse.meetings.map { it.toMeeting(calculation.teams) }.toSet(),
            meetings = planResponse.meetings.map { it.toMeeting(fetchedTeams) }.toSet(),
            additionalInformation = planResponse.additionalInformation,
            fitness = planResponse.fitness,
        )

        val savedPlan = planService.add(plan)
        return savedPlan
    }

    @Transactional
    open fun getAll(): Set<Calculation> {
        logger.debug { "Getting all calculations..." }
        val calculations = calculationRepository.findAll()
        logger.debug { "Got ${calculations.count()} calculations" }

        logger.debug { "Populating ${calculations.count()} calculations..." }
        val populatedCalculations = calculations
            .map { get(it.id!!) }
            .toSet()
        logger.debug { "Got ${populatedCalculations.count()} populated calculations" }

        return populatedCalculations
    }

    @Transactional
    open fun add(
        surveyCsv: String,
        populationSize: Int,
        fitnessThreshold: Double,
        steadyFitness: Int
    ): Calculation {
        // TODO: importTeams() is long running and blocking because it fetches Geolocations
        val teams = csvTeamsImporter.importTeams(surveyCsv)
        return add(teams, populationSize, fitnessThreshold, steadyFitness)
    }

    fun add(
        teams: List<Team>,
        populationSize: Int,
        fitnessThreshold: Double,
        steadyFitness: Int
    ): Calculation {
        logger.debug { "Adding calculation..." }

        // TODO: uses hardcoded coursesNames for now; should be more flexible
        val coursesNames = Courses.coursesNames
        val calculation = Calculation(
            null,
            false,
            populationSize,
            fitnessThreshold,
            steadyFitness,
            null,
            teams,
            null,
            null,
            null,
            coursesNames
        )

        val savedCalculation = add(calculation)

        logger.debug { "Added calculation" }
        return savedCalculation
    }

    fun add(calculation: Calculation): Calculation {
        logger.debug { "Adding plan '$calculation'..." }
        val savedCalculation = calculationRepository.save(calculation)
        logger.debug { "Added plan: $savedCalculation" }
        return savedCalculation
    }

    fun start(id: UUID): Calculation {
        val calculation = this.get(id)

        val calculationRequest = CalculationRequest(
            populationSize = calculation.populationSize,
            steadyFitness = calculation.steadyFitness,
            fitnessThreshold = calculation.fitnessThreshold,
            teams = calculation.teams.map { TeamRequest(it) },
            coursesNames = calculation.coursesNames,
        )

        logger.debug { "Sending CalculationRequest: $calculationRequest..." }
        val calculationResponse = calculationClient.postOne(calculationRequest)
        logger.debug { "Received CalculationResponse: $calculationResponse..." }

        // TODO: here a save() is needed; I'm not sure why I don't just save() the calculation afterwards...
        calculation.calculationId = calculationResponse.id

        return calculation
    }

    class CalculationNotFoundException(planId: UUID) : Exception("Plan $planId not found")
}