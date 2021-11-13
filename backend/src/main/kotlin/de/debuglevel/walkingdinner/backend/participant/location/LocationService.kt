package de.debuglevel.walkingdinner.backend.participant.location

import jakarta.inject.Singleton
import mu.KotlinLogging
import java.util.*
import javax.transaction.Transactional

@Singleton
open class LocationService(
    private val locationRepository: LocationRepository,
) {
    private val logger = KotlinLogging.logger {}

    fun get(id: UUID): Location {
        logger.debug { "Getting location '$id'..." }
        val location = locationRepository.findById(id).orElseThrow { LocationNotFoundException(id) }
        logger.debug { "Got location: $location" }
        return location
    }

    @Transactional
    open fun getAll(): Set<Location> {
        logger.debug { "Getting all locations..." }
        val locations = locationRepository.findAll().toSet()
        logger.debug { "Got all locations" }
        return locations
    }

    @Transactional
    open fun save(location: Location): Location {
        logger.debug { "Saving location '$location'..." }
        val savedLocation = locationRepository.save(location)
        logger.debug { "Saved location: $savedLocation" }
        return savedLocation
    }

    class LocationNotFoundException(locationId: UUID) : Exception("Location $locationId not found")
}