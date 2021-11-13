package de.debuglevel.walkingdinner.backend

import de.debuglevel.walkingdinner.backend.participant.Team
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class Meeting(
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val teams: List<Team>,

    val course: String,

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID? = null
) {

    /**
     * Gets the team which is cooking in this meeting.
     * @implNote The cooking team is defined as the first one in the list.
     */
    fun getCookingTeam(): Team {
        return teams.first()
    }

    override fun toString(): String {
        return "Meeting(" +
                "id=$id" +
                "course='$course', " +
                ")"
    }
}