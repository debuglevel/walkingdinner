package de.debuglevel.walkingdinner.rest.participant

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
}