package de.debuglevel.walkingdinner.planner.location

/**
 * A [Location] defined by its [longitude] and [latitude].
 */
data class Location(
    val longitude: Double,
    val latitude: Double
) {
    override fun toString(): String {
        return "Location(" +
                "longitude=$longitude, " +
                "latitude=$latitude" +
                ")"
    }

    /**
     * @implNote: Not sure if this implementation without an ID might have strange side effects (e.g. multiple teams at the same location)
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Location

        if (longitude != other.longitude) return false
        if (latitude != other.latitude) return false

        return true
    }

    /**
     * @implNote: Not sure if this implementation without an ID might have strange side effects (e.g. multiple teams at the same location)
     */
    override fun hashCode(): Int {
        var result = longitude.hashCode()
        result = 31 * result + latitude.hashCode()
        return result
    }
}
