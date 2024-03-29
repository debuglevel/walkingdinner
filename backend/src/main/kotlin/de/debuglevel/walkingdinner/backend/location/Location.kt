package de.debuglevel.walkingdinner.backend.location

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Location(
    var address: String,

    var longitude: Double,

    var latitude: Double,

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID? = null
) {
    constructor() : this("", 0.0, 0.0)

    override fun toString(): String {
        return "Location(" +
                "address='$address', " +
                "longitude=$longitude, " +
                "latitude=$latitude, " +
                "id=$id" +
                ")"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Location

        if (address != other.address) return false

        return true
    }

    override fun hashCode(): Int {
        return address.hashCode()
    }

}