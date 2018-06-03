package rocks.huwi.walkingdinner.geneticplanner

import io.jenetics.util.ISeq
import rocks.huwi.walkingdinner.geneticplanner.team.Team
import java.util.*

data class Courses(val course1teams: ISeq<Team>,
                   val course2teams: ISeq<Team>,
                   val course3teams: ISeq<Team>) {

    companion object {
        const val course1name = "Vorspeise"
        const val course2name = "Hauptspeise"
        const val course3name = "Dessert"
    }

    fun toMeetings(): Set<Meeting> {
        val meetings = HashSet<Meeting>()
        meetings.addAll(Team.toMeetings(course1teams, course1name))
        meetings.addAll(Team.toMeetings(course2teams, course2name))
        meetings.addAll(Team.toMeetings(course3teams, course3name))

        return meetings
    }

    fun toCourseMeetings(): Map<String, Set<Meeting>> {
        val courseMeetings = hashMapOf<String, Set<Meeting>>()

        courseMeetings[course1name] = Team.toMeetings(course1teams, course1name)
        courseMeetings[course2name] = Team.toMeetings(course2teams, course2name)
        courseMeetings[course3name] = Team.toMeetings(course3teams, course3name)

        return courseMeetings
    }

    fun print() {
        this.toMeetings()
                .groupBy { it.course }
                .forEach { course, meetings ->
                    run {
                        println()
                        println("== Course $course")
                        meetings.forEach { println(it) }
                    }
                }
    }
}