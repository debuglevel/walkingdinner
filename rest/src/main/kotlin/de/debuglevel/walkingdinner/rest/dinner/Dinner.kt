package de.debuglevel.walkingdinner.rest.dinner

import de.debuglevel.walkingdinner.rest.organisation.Organisation
import de.debuglevel.walkingdinner.rest.participant.Team
import de.debuglevel.walkingdinner.rest.plan.calculation.Calculation
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class Dinner(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID? = null,

    val name: String,

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val teams: Set<Team> = setOf(),

    @OneToMany(cascade = [CascadeType.ALL])
    val calculations: Set<Calculation> = setOf(),

    val city: String,

    val begin: LocalDateTime,

    @ManyToOne
    val organisation: Organisation
) {
    override fun toString(): String {
        return "Dinner(" +
                "id=$id, " +
                "name='$name', " +
                "city='$city', " +
                "begin=$begin" +
                ")"
    }
}