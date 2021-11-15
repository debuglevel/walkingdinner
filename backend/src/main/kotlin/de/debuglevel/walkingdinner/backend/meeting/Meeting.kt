package de.debuglevel.walkingdinner.backend.meeting

import de.debuglevel.walkingdinner.backend.team.Team
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class Meeting(
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val teams: List<Team>,

    val courseName: String,

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
                "courseName='$courseName', " +
                ")"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Meeting

        if (teams != other.teams) return false
        if (courseName != other.courseName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = teams.hashCode()
        result = 31 * result + courseName.hashCode()
        return result
    }
}