package de.debuglevel.walkingdinner.backend.dinner

import de.debuglevel.walkingdinner.backend.organisation.OrganisationService
import io.micronaut.context.BeanContext
import jakarta.inject.Singleton
import mu.KotlinLogging
import java.util.*
import javax.transaction.Transactional

@Singleton
open class DinnerService(
    private val dinnerRepository: DinnerRepository,
    private val beanContext: BeanContext,
) {
    private val logger = KotlinLogging.logger {}

    /**
     * @implNote In case [OrganisationService] also wants to access [DinnerService],
     *  this allows a circular dependency as they are not set during initialization.
     */
    private val organisationService: OrganisationService
        get() = beanContext.getBean(OrganisationService::class.java)

    fun get(id: UUID): Dinner {
        logger.debug { "Getting dinner '$id'..." }
        val dinner = dinnerRepository.findById(id).orElseThrow { DinnerNotFoundException(id) }
        logger.debug { "Got dinner: $dinner" }
        return dinner
    }

    @Transactional
    open fun getAll(): Set<Dinner> {
        logger.debug { "Getting all dinners..." }
        val dinners = dinnerRepository.findAll().toSet()
        logger.debug { "Got all dinners" }
        return dinners
    }

    @Transactional
    open fun save(dinner: Dinner): Dinner {
        logger.debug { "Saving dinner '$dinner'..." }
        // Load organisation again inside this transaction; would throw an LazyInitializationException otherwise
        val organisation = organisationService.get(dinner.organisation.id!!)
        val savingDinner = dinner.copy(organisation = organisation)
        val savedDinner = dinnerRepository.save(savingDinner)
        logger.debug { "Saved dinner: $savedDinner" }
        return savedDinner
    }

    class DinnerNotFoundException(dinnerId: UUID) : Exception("Dinner $dinnerId not found")
}