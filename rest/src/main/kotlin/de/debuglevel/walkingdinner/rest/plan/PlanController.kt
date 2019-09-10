package de.debuglevel.walkingdinner.rest.plan

//import spark.kotlin.RouteHandler
import de.debuglevel.walkingdinner.cli.performance.TimeMeasurement
import de.debuglevel.walkingdinner.importer.Database
import de.debuglevel.walkingdinner.planner.geneticplanner.CoursesProblem
import de.debuglevel.walkingdinner.planner.geneticplanner.GeneticPlanner
import de.debuglevel.walkingdinner.planner.geneticplanner.GeneticPlannerOptions
import de.debuglevel.walkingdinner.report.teams.summary.SummaryReporter
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.responsetransformer.JsonTransformer
import de.debuglevel.walkingdinner.utils.MultipartUtils
import io.jenetics.EnumGene
import io.jenetics.engine.EvolutionResult
import io.jenetics.engine.EvolutionStatistics
import io.jenetics.stat.DoubleMomentStatistics
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import mu.KotlinLogging
import java.nio.file.Path
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.function.Consumer

@Controller("/plans")
class PlanController(private val planService: PlanService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{planId}")
    fun getOne(planId: String): PlanDTO {
        logger.debug("Called getOne($planId)")
        return planService.get(planId)
    }

    @Get("/")
    fun getList(): Set<PlanDTO> {
        logger.debug("Called getList()")
        return planService.getAll()
    }




    private val plans = mutableMapOf<UUID, Future<Plan>>()

    private val executor = Executors.newFixedThreadPool(4)

    fun postOneX(): Any {

//            logger.debug("Got POST request on '/plans' with content-type '${request.contentType()}'")
//
//            if (!request.contentType().startsWith("multipart/form-data")) {
//                logger.debug { "Declining POST request with unsupported content-type '${request.contentType()}'" }
//                throw Exception("Content-Type ${request.contentType()} not supported.")
//            }

        val request = Object()

            // get supplied multipart values
            val surveyCsvFile = MultipartUtils.getFile(request, "surveyfile")
            val populationsSize = MultipartUtils.getField(request, "populationsSize").toInt()
            val fitnessThreshold = MultipartUtils.getField(request, "fitnessThreshold").toDouble()
            val steadyFitness = MultipartUtils.getField(request, "steadyFitness").toInt()
            val location = MultipartUtils.getField(request, "location")

            // start planner
            val plannerTask = Callable<Plan> {
                val startPlanner = try {
                    startPlanner(surveyCsvFile, populationsSize, fitnessThreshold, steadyFitness, location)
                } catch (e: Exception) {
                    logger.error("Callable threw exception", e)
                    throw e
                }

                startPlanner
            }

            val plannerFuture = executor.submit(plannerTask)
            val planId = UUID.randomUUID()
            plans[planId] = plannerFuture

        return "Computing plan /plans/$planId ..."

    }

    private fun startPlanner(fileName: Path,
                             populationsSize: Int,
                             fitnessThreshold: Double,
                             steadyFitness: Int,
                             location: String): Plan {
        val evolutionStatistics = EvolutionStatistics.ofNumber<Double>()
        val consumers: Consumer<EvolutionResult<EnumGene<Team>, Double>>? = Consumer {
            evolutionStatistics.accept(it)
            printIntermediary(it)
        }

        val database = Database(fileName, location)

        val options = GeneticPlannerOptions(
                evolutionResultConsumer = consumers,
                teams = database.teams,
                populationsSize = populationsSize,
                fitnessThreshold = fitnessThreshold,
                steadyFitness = steadyFitness
        )

        val plan = GeneticPlanner(options).plan()

        //processResults(result, evolutionStatistics)

        return plan
    }

    private fun processResults(result: EvolutionResult<EnumGene<Team>, Double>,
                               evolutionStatistics: EvolutionStatistics<Double, DoubleMomentStatistics>?) {
        println()
        println("Best in Generation: " + result.generation)
        println("Best with Fitness: " + result.bestFitness)

        println()
        println(evolutionStatistics)

        println()
        val courses = CoursesProblem(result.bestPhenotype.genotype.gene.validAlleles)
                .codec()
                .decode(result.bestPhenotype.genotype)
        val meetings = courses.toMeetings()

        SummaryReporter().generateReports(meetings)
//        GmailDraftReporter().generateReports(meetings)
    }

    private fun printIntermediary(e: EvolutionResult<EnumGene<Team>, Double>) {
        TimeMeasurement.add("evolveDuration", e.durations.evolveDuration.toNanos(), 500)
        if (e.generation % 500 == 0L) {
            println("${Math.round(1 / (e.durations.evolveDuration.toNanos() / 1_000_000_000.0))}gen/s\t| Generation: ${e.generation}\t| Best Fitness: ${e.bestFitness}")
        }
    }

    fun getOneX(): String {

//            type(contentType = "application/json")
//            val dinnerId = request.params(":dinnerId").toUUID()
        //val planId = request.params(":planId").toUUID()
        val planId = UUID.randomUUID()

            val future = plans[planId]

        return if (future == null) {
//                type(contentType = "plain/text")
//                status(404)
                "Plan not found"
            } else {
//                type(contentType = "application/json")
                val plan = when {
                    future.isDone -> PlanDTO(true, future.get())
                    else -> PlanDTO(false)
                }
                JsonTransformer.render(plan)
            }
    }

//    fun getAddFormHtml(): RouteHandler.() -> String {
//        return {
//            val model = HashMap<String, Any>()
//            MustacheTemplateEngine().render(ModelAndView(model, "plan/add.html.mustache"))
//        }
//    }
}