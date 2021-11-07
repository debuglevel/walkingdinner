package de.debuglevel.walkingdinner.rest.participant.location

import io.jsondb.annotation.Document
import io.jsondb.annotation.Id
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue

@Entity
@Document(collection = "locations", schemaVersion = "1.0")
data class Location(
    @Id
    var address: String,

    var longitude: Double,

    var latitude: Double,

    @javax.persistence.Id
    @GeneratedValue
    val id: UUID? = null
) {
    constructor() : this("", 0.0, 0.0)

    override fun toString(): String {
        return "$address ($latitude, $longitude)"
    }
}