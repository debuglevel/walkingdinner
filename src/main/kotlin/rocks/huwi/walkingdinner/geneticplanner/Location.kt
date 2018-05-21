package rocks.huwi.walkingdinner.geneticplanner

import io.jsondb.annotation.Document
import io.jsondb.annotation.Id

@Document(collection = "locations", schemaVersion = "1.0")
data class Location(@Id var address: String,
                    var lng: Double,
                    var lat: Double) {

    constructor() : this("", 0.0, 0.0)

    fun calculateDistance(location: Location): Double = GeoUtils.calculateDistanceInKilometer(this, location)
}