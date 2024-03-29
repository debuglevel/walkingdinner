package de.debuglevel.walkingdinner.backend.dinner

import de.debuglevel.walkingdinner.backend.common.ElementNotFoundException
import de.debuglevel.walkingdinner.backend.organisation.OrganisationService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging
import java.util.*

@Controller("/dinners")
class DinnerController(private val dinnerService: DinnerService, private val organisationService: OrganisationService) {
    private val logger = KotlinLogging.logger {}

    @Get("/{dinnerId}")
    fun getOne(dinnerId: UUID): HttpResponse<DinnerResponse> {
        logger.debug("Called getOne($dinnerId)")

        return try {
            val dinner = dinnerService.get(dinnerId)
            HttpResponse.ok(DinnerResponse(dinner))
        } catch (e: ElementNotFoundException) {
            HttpResponse.notFound<DinnerResponse>()
        }
    }

    @Get("/")
    fun getList(): Set<DinnerResponse> {
        logger.debug("Called getList()")
        val dinners = dinnerService.getAll()
        return dinners.map { DinnerResponse(it) }.toSet()
    }

    @Post("/")
    fun postOne(dinnerRequest: DinnerRequest): HttpResponse<DinnerResponse> {
        logger.debug("Called postOne()")

        return try {
            val dinner = dinnerRequest.toDinner(organisationService)
            val savedDinner = dinnerService.save(dinner)
            HttpResponse.created(DinnerResponse(savedDinner))
        } catch (e: ElementNotFoundException) {
            HttpResponse.badRequest<DinnerResponse>()
        }
    }
}