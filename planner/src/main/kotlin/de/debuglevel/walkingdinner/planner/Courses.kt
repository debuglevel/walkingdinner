package de.debuglevel.walkingdinner.planner

data class Courses(
    val course1teams: Iterable<Team>,
    val course2teams: Iterable<Team>,
    val course3teams: Iterable<Team>
) {

    // TODO: should be labeled in english
    companion object {
        const val course1name = "Vorspeise"
        const val course2name = "Hauptspeise"
        const val course3name = "Dessert"
    }

    fun toMeetings(): Set<Meeting> {
        return toCourseMeetings()
            .values
            .flatten()
            .toSet()
    }

    fun toCourseMeetings(): Map<String, Set<Meeting>> {
        val courseMeetings = hashMapOf<String, Set<Meeting>>()

        courseMeetings[course1name] = teamToMeetings(
            course1teams,
            course1name
        )
        courseMeetings[course2name] = teamToMeetings(
            course2teams,
            course2name
        )
        courseMeetings[course3name] = teamToMeetings(
            course3teams,
            course3name
        )

        return courseMeetings
    }

    /**
     * Transforms a list of sequential [Team]s to a set of [Meeting]s.
     * TODO: Should probably throw an exception if an unsuitable number of teams was supplied.
     */
    private fun teamToMeetings(teams: Iterable<Team>, courseName: String): Set<Meeting> {
        val meetingSize = 3

        val meetings = mutableSetOf<Meeting>()

        // Create an empty array to be filled with 3 teams
        val meetingTeams: Array<Team?> = Array(meetingSize) { null }

        // Iterate through all teams
        for ((index, value) in teams.withIndex()) {
            // Put each team at its position in the array
            meetingTeams[index % meetingSize] = value

            // If the array is full...
            if (index % meetingSize == meetingSize - 1) {
                // Create a meeting with this teams
                val meeting = Meeting(
                    courseName,
                    meetingTeams.filterNotNull() // Ensure not-null and clones the list
                )
                meetings.add(meeting)
            }
        }

        return meetings
    }
}