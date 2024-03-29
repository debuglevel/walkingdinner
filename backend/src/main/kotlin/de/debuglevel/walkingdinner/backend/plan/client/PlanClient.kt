package de.debuglevel.walkingdinner.backend.plan.client

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import java.util.*

@Client("planner", path = "/plans")
interface PlanClient {
    @Get("/{planId}")
    fun getOne(planId: UUID): PlanResponse

    @Get("/")
    fun getList(): Set<PlanResponse>
}