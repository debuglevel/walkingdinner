package de.debuglevel.walkingdinner.backend.organisation

import de.debuglevel.walkingdinner.backend.common.MailAddress
import de.debuglevel.walkingdinner.backend.dinner.Dinner
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class Organisation(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Organisation

        if (mail != other.mail) return false

        return true
    }

    override fun hashCode(): Int {
        return mail.hashCode()
    }
}