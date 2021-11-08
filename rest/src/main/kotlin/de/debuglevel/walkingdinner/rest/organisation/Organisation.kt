package de.debuglevel.walkingdinner.rest.organisation

import de.debuglevel.walkingdinner.rest.MailAddress
import de.debuglevel.walkingdinner.rest.dinner.Dinner
import java.util.*
import javax.persistence.*

@Entity
data class Organisation(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    val mail: MailAddress,

    val name: String,

    @OneToMany(cascade = [CascadeType.ALL])
    val dinners: Set<Dinner> = setOf()
) {
    override fun toString(): String {
        return "Organisation(" +
                "id=$id, " +
                "mail=$mail, " +
                "name='$name'" +
                ")"
    }
}