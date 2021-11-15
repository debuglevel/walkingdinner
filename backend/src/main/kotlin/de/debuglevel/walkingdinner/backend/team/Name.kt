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

    val name: String
) {
    val firstname = extractFirstname()

    val lastname = extractLastname()

    val abbreviatedName = run {
        val firstletter = firstname.toCharArray().first()
        "$firstletter. $lastname"
    }

    private fun extractFirstname(): String {
        return this.name.split(" ").first()
    }

    private fun extractLastname(): String {
        return this.name.split(" ").last()
    }

    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Name

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

}