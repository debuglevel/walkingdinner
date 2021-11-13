package de.debuglevel.walkingdinner.backend.plan.calculation.client

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import java.util.*

@Client("planner", path = "/calculations")
interface CalculationClient {
    @Get("/{calculationId}")
    fun getOne(calculationId: UUID): CalculationResponse

    @Get("/")
    fun getList(): Set<CalculationResponse>

    @Post("/")
    fun postOne(calculationRequest: CalculationRequest): CalculationResponse
}