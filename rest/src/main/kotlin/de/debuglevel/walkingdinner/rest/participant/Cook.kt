package de.debuglevel.walkingdinner.rest.participant

import de.debuglevel.walkingdinner.rest.MailAddress
import de.debuglevel.walkingdinner.rest.PhoneNumber
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
    val name: Name,

    @OneToOne(cascade = [CascadeType.ALL])
    val mailAddress: MailAddress,

    @OneToOne(cascade = [CascadeType.ALL])
    val phoneNumber: PhoneNumber
) {
    override fun toString(): String {
        //return "$name ($mail, $phoneNumber)"
        return "Cook(id=$id)"
    }
}