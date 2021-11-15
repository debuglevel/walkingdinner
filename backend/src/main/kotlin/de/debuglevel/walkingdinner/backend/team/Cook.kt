package de.debuglevel.walkingdinner.backend.team

import de.debuglevel.walkingdinner.backend.common.MailAddress
import de.debuglevel.walkingdinner.backend.common.PhoneNumber
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class Cook(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    val name: de.debuglevel.walkingdinner.backend.team.Name,

    @OneToOne(cascade = [CascadeType.ALL])
    val mailAddress: MailAddress,

    @OneToOne(cascade = [CascadeType.ALL])
    val phoneNumber: PhoneNumber
) {
    override fun toString(): String {
        //return "$name ($mail, $phoneNumber)"
        return "Cook(id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as de.debuglevel.walkingdinner.backend.team.Cook

        if (mailAddress != other.mailAddress) return false

        return true
    }

    override fun hashCode(): Int {
        return mailAddress.hashCode()
    }
}