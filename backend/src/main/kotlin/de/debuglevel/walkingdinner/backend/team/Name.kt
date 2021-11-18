package de.debuglevel.walkingdinner.backend.team

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Name(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID? = null,

    val firstname: String,

    val lastname: String,
) {
    val fullName: String
        get() = "$firstname $lastname"

    val abbreviatedName = run {
        val firstLetter = firstname.toCharArray().first()
        "$firstLetter. $lastname"
    }

    override fun toString(): String {
        return "Name(id=$id, firstname='$firstname', lastname='$lastname')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Name

        if (firstname != other.firstname) return false
        if (lastname != other.lastname) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstname.hashCode()
        result = 31 * result + lastname.hashCode()
        return result
    }
}