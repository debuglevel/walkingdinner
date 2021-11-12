package de.debuglevel.walkingdinner.rest.plan

import de.debuglevel.walkingdinner.rest.Meeting
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class Plan(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID? = null,

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val meetings: Set<Meeting>,

    val additionalInformation: String
) {
    override fun toString(): String {
        return "Plan(id=$id)"
    }
}
