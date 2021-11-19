package de.debuglevel.walkingdinner.backend.plan

import de.debuglevel.walkingdinner.backend.meeting.Meeting
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

    @Lob
    val additionalInformation: String,

    val fitness: Double,
) {
    override fun toString(): String {
        return "Plan(id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Plan

        if (additionalInformation != other.additionalInformation) return false

        return true
    }

    override fun hashCode(): Int {
        return additionalInformation.hashCode()
    }
}
