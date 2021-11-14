package de.debuglevel.walkingdinner.planner.planner.geneticplanner

import de.debuglevel.walkingdinner.planner.Courses
import de.debuglevel.walkingdinner.planner.Location
import de.debuglevel.walkingdinner.planner.Meeting
import de.debuglevel.walkingdinner.planner.Team
import de.debuglevel.walkingdinner.planner.common.GeoUtils
import de.debuglevel.walkingdinner.planner.dietcompatibility.CourseDietCompatibility
import io.jenetics.EnumGene
import io.jenetics.Genotype
import io.jenetics.PermutationChromosome
import io.jenetics.engine.Codec
import io.jenetics.engine.Problem
import io.jenetics.util.ISeq
import java.util.function.Function
import java.util.stream.Collectors

class CoursesProblem(private val teams: ISeq<Team>) : Problem<Courses, EnumGene<Team>, Double> {
    override fun codec(): Codec<Courses, EnumGene<Team>> {
        val encoding: Genotype<EnumGene<Team>> = Genotype.of(
            PermutationChromosome.of(teams),
            PermutationChromosome.of(teams),
            PermutationChromosome.of(teams)
        )

        val decoder: Function<Genotype<EnumGene<Team>>, Courses> =
            Function { genotype: Genotype<EnumGene<Team>> ->
                Courses(
                    genotype.get(0).map { it.validAlleles().get(it.alleleIndex()) },
                    genotype.get(1).map { it.validAlleles().get(it.alleleIndex()) },
                    genotype.get(2).map { it.validAlleles().get(it.alleleIndex()) }
                )
            }

        val codec: Codec<Courses, EnumGene<Team>> = Codec.of(
            encoding,
            decoder
        )

        return codec
    }

    override fun fitness(): Function<Courses, Double> {
        return Function { courses ->
            val courseMeetings = courses.toCourseMeetings()
            val meetings = courseMeetings.entries.stream()
                .flatMap { courseMeeting -> courseMeeting.value.stream() }
                .collect(Collectors.toSet<Meeting>())

            val multipleCookingTeamsMalus = 1 * calculateMultipleCookingTeams(
                meetings
            )
            val incompatibleTeamsCourse1Malus = 1 * calculateIncompatibleMeetings(
                courseMeetings.getValue(Courses.course1name)
            )
            val incompatibleTeamsCourse2Malus = 1 * calculateIncompatibleMeetings(
                courseMeetings.getValue(Courses.course2name)
            )
            val incompatibleTeamsCourse3Malus = 1 * calculateIncompatibleMeetings(
                courseMeetings.getValue(Courses.course3name)
            )
            val overallDistanceMalus = 0.00001 * calculateOverallDistance(courses)

            val fitness =
                multipleCookingTeamsMalus +
                        incompatibleTeamsCourse1Malus +
                        incompatibleTeamsCourse2Malus +
                        incompatibleTeamsCourse3Malus +
                        overallDistanceMalus

            fitness
        }
    }

    companion object {
        private fun getTeamLocations(courseMeetings: Map<String, List<Meeting>>): HashMap<Team, MutableList<Location>> {
            val teamsLocations = HashMap<Team, MutableList<Location>>()

            addLocations(
                teamsLocations,
                courseMeetings.getValue(Courses.course1name)
            )
            addLocations(
                teamsLocations,
                courseMeetings.getValue(Courses.course2name)
            )
            addLocations(
                teamsLocations,
                courseMeetings.getValue(Courses.course3name)
            )

            return teamsLocations
        }

        /**
         * Calculates how many meetings have incompatible teams.
         */
        private fun calculateIncompatibleMeetings(meetings: Set<Meeting>): Int {
            return meetings
                // .filter(m -> !HardCompatibility.INSTANCE.areCompatibleTeams(m))
                .filter { meeting -> !CourseDietCompatibility.areCompatibleTeams(meeting) }
                .count()
        }

        /**
         * Calculates the distance (in kilometers) all teams have to travel in sum.
         */
        private fun calculateOverallDistance(courses: Courses): Double {
            val meetings = courses.toMeetings()

            val courseMeetings = meetings.groupBy { it.course }

            val teamsLocations = getTeamLocations(courseMeetings)

            return teamsLocations.values
                .map { GeoUtils.calculateLocationsDistance(it) }
                .sum()
        }

        private fun addLocations(teamsLocations: HashMap<Team, MutableList<Location>>, meetings: List<Meeting>) {
            for (meeting in meetings) {
                for (team in meeting.teams) {
                    // Get item in HashMap or create empty List if not already available
                    val teamLocations = teamsLocations.computeIfAbsent(team) { mutableListOf() }

                    teamLocations.add(meeting.getCookingTeam().location)
                }
            }
        }

        /**
         * Calculates how many teams cook more than once in the given [meetings].
         */
        private fun calculateMultipleCookingTeams(meetings: Set<Meeting>): Int {
            // Find out how often each team cooks
            val teamCookingCount = meetings
                .map { it.getCookingTeam() }
                .groupBy { it }
                .mapValues { it.value.count() }

            // Count how many teams cook more than once
            val multipleCookingTeamsCount = teamCookingCount.values
                .filter { it > 1 }
                .sumOf { it }

            return multipleCookingTeamsCount
        }
    }
}
