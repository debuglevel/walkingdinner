package de.debuglevel.walkingdinner.backend.team.location.locator

import de.debuglevel.walkingdinner.backend.common.geo.GeoUtils
import de.debuglevel.walkingdinner.backend.team.Team
import de.debuglevel.walkingdinner.backend.team.location.Location
import fr.dudie.nominatim.client.JsonNominatimClient
import fr.dudie.nominatim.client.request.NominatimSearchRequest
import fr.dudie.nominatim.model.Address
import jakarta.inject.Singleton
import mu.KotlinLogging
import org.apache.http.impl.client.HttpClientBuilder
import java.text.DecimalFormat
import kotlin.concurrent.withLock

@Singleton
class NominatimApiGeolocator : Geolocator {
    private val logger = KotlinLogging.logger {}

    private val singleRequestLock = java.util.concurrent.locks.ReentrantLock()

    private val nominatimClient: JsonNominatimClient
        get() = buildNominatimClient()

    override fun getLocation(address: String?, city: String): Location {
        logger.debug { "Getting location for address '$address' in city '$city'..." }

        val cityAddress = if (address.isNullOrBlank()) city else "$address, $city"
        val result = try {
            getNominatimAddress(cityAddress)
        } catch (e: NoAddressesFoundException) {
            getNominatimAddress(city)
        }

        val location = Location(
            cityAddress,
            result.longitude,
            result.latitude
        )

        logger.debug { "Got location for address '$address' in city '$city': $location" }
        return location
    }

    override fun initializeTeamLocation(team: Team) {
        logger.debug("Geo-locating team '$team'...")

        val location = getLocation(team.address, team.city)
        team.location = location

        val cityLocation = getLocation(null, team.city)

        val distanceToCity = GeoUtils.calculateDistanceInKilometer(cityLocation, location)

        logger.debug("Geo-located team '$team' ${DecimalFormat("#.##").format(distanceToCity)}km from city center")
    }

    private fun buildNominatimClient(): JsonNominatimClient {
        logger.trace { "Building Nominatim client..." }

        val httpClient = HttpClientBuilder.create().build()

        val baseUrl = "https://nominatim.openstreetmap.org/"
        val email = "debuglevel.de"
        val jsonNominatimClient = JsonNominatimClient(baseUrl, httpClient, email)

        logger.trace { "Built Nominatim client" }
        return jsonNominatimClient
    }

    private fun getNominatimAddress(cityAddress: String): Address {
        logger.debug("Searching address '$cityAddress'...")

        val searchRequest = NominatimSearchRequest()
        searchRequest.setQuery(cityAddress)

        // OpenStreetMaps Nominatim API allows only 1 concurrent connection. Ensure this with a lock.
        logger.debug("Waiting for lock to call NominatimClient for address '$cityAddress'...")
        val addresses = singleRequestLock.withLock {
            logger.debug("Calling NominatimClient for address '$cityAddress'...")
            val addresses = nominatimClient.search(searchRequest)
            logger.debug("Called NominatimClient for address '$cityAddress': ${addresses.size} results.")
            addresses
        }

        if (addresses.isEmpty()) {
            logger.warn { "No address found for '$cityAddress'" }
            throw NoAddressesFoundException(cityAddress)
        }

        val result = addresses[0]
        // TODO: if available, prefer class="building"

        logger.debug("Searched address '$cityAddress': ${result.displayName}")
        return result
    }

    class NoAddressesFoundException(address: String) :
        Exception("Noresults found for address '$address'")
}