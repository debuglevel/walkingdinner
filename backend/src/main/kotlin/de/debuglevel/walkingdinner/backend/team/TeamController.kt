package de.debuglevel.walkingdinner.backend.team

import de.debuglevel.walkingdinner.backend.common.ElementNotFoundException
import de.debuglevel.walkingdinner.backend.dinner.DinnerService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging
import java.util.*

@Controller("/teams")
class TeamController(private val teamService: TeamService, private val dinnerService: DinnerService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{teamId}")
    fun getOne(teamId: UUID): HttpResponse<TeamResponse> {
        logger.debug("Called getOne($teamId)")

        return try {
            val team = teamService.get(teamId)
            HttpResponse.ok(TeamResponse(team))
        } catch (e: ElementNotFoundException) {
            HttpResponse.notFound<TeamResponse>()
        }
    }

    @Get("/")
    fun getList(): Set<TeamResponse> {
        logger.debug("Called getList()")
        val teams = teamService.getAll()
        return teams.map { TeamResponse(it) }.toSet()
    }

    @Post("/")
    fun postOne(teamRequest: TeamRequest): HttpResponse<TeamResponse> {
        logger.debug("Called postOne()")

        return try {
            val team = teamRequest.toTeam(dinnerService)
            val savedTeam = teamService.save(team)
            HttpResponse.created(TeamResponse(savedTeam))
        } catch (e: ElementNotFoundException) {
            HttpResponse.badRequest<TeamResponse>()
        }
    }
}