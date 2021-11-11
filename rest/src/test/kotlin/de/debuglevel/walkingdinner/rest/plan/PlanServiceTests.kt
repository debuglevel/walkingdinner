package de.debuglevel.walkingdinner.rest.plan

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.TestInstance

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlanServiceTests {

    @Inject
    lateinit var planService: PlanService

//    @Test
//    fun addX() {
//        val plan = Plan(null, setOf<Meeting>(), "test")
//        planService.addX(plan)
//    }
}