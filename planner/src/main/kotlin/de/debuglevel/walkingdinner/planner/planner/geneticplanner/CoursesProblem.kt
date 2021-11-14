package de.debuglevel.walkingdinner.planner.planner.geneticplanner

import de.debuglevel.walkingdinner.planner.Courses
import de.debuglevel.walkingdinner.planner.Location
import de.debuglevel.walkingdinner.planner.Meeting
import de.debuglevel.walkingdinner.planner.Team
import de.debuglevel.walkingdinner.planner.common.geo.GeoUtils
import de.debuglevel.walkingdinner.planner.dietcompatibility.CourseDietCompatibility
import io.jenetics.EnumGene
import io.jenetics.Genotype
import io.jenetics.PermutationChromosome
import io.jenetics.engine.Codec
import io.jenetics.engine.Problem
import io.jenetics.util.ISeq
import java.util.function.Function

class CoursesProblem(
    private val teams: ISeq<Team>,
    private val courseNames: List<String>,
) : Problem<Courses, EnumGene<Team>, Double> {
    override fun codec(): Codec<Courses, EnumGene<Team>> {
        // Encode the problem domain into a Genotype which can be evolved
        // Create a Chromosome for each Course. Create a Genotype from all Chromosomes.
        val chromosomes: List<PermutationChromosome<Team>> = courseNames.map { PermutationChromosome.of(teams) }
        val encoding: Genotype<EnumGene<Team>> = Genotype.of(chromosomes)

        // Decodes the Genotype back into the problem domain (where e.g. fitness can be evaluated)
        val decoder: Function<Genotype<EnumGene<Team>>, Courses> =
            Function { genotype: Genotype<EnumGene<Team>> ->
                // Create a List with all Courses with all ordered Teams
                val coursesTeams = genotype.map { chromosome ->
                    chromosome.map { enumGene ->
                        enumGene.validAlleles().get(enumGene.alleleIndex())
                    }
                }

                // Associate each List of Teams (i.e. Teams ordered in Course) with its name
                val coursesTeams2 = courseNames zip coursesTeams
                Courses(coursesTeams2)
            }

        val codec: Codec<Courses, EnumGene<Team>> = Codec.of(
            encoding,
            decoder
        )

        return codec
    }

    /**
     * Calculates the [fitness] of a [Courses] object.
     * @implNote: As weird things might happen with a genetic approach, use [List]s instead of [Set]s so that no deduplication will occur and order is preserved.
     */
    override fun fitness(): Function<Courses, Double> {
        return Function { courses ->
            val coursesMeetings = courses.toCoursesMeetings()

            val meetings = coursesMeetings.values.flatten()

            val multipleCookingTeamsMalus = 1 * calculateMultipleCookingTeams(meetings)

            val incompatibleTeamsMalus = coursesMeetings.values.sumOf { 1 * calculateIncompatibleMeetings(it) }

            val overallDistanceMalus = 0.00001 * calculateOverallDistance(courses.orderedCoursesNames, coursesMeetings)

            val fitness =
                multipleCookingTeamsMalus +
                        incompatibleTeamsMalus +
                        overallDistanceMalus

            fitness
        }
    }

    companion object {
        /**
         * Calculates how many meetings have incompatible teams (according to their diet and cooking capabilities).
         */
        private fun calculateIncompatibleMeetings(meetings: List<Meeting>): Int {
            // .count{ m -> !HardCompatibility.INSTANCE.areCompatibleTeams(m) }
            return meetings.count { meeting -> !CourseDietCompatibility.isCompatible(meeting) }
        }

        /**
         * Calculates the distance (in kilometers) all teams have to travel in sum.
         */
        private fun calculateOverallDistance(
            orderedCoursesNames: List<String>,
            coursesMeetings: Map<String, List<Meeting>>
        ): Double {
            val teamsLocations = getTeamLocations(orderedCoursesNames, coursesMeetings)

            return teamsLocations.values.sumOf { GeoUtils.calculateLocationsDistance(it) }
        }

        /**
         * Gets the ordered [Location]s for each [Team].
         */
        private fun getTeamLocations(
            orderedCoursesNames: List<String>,
            coursesMeetings: Map<String, List<Meeting>>
        ): HashMap<Team, MutableList<Location>> {
            // Create a HashMap with an empty List for each Team
            val teamsLocations = HashMap<Team, MutableList<Location>>()
            coursesMeetings.values.flatten().map { it.teams }.flatten().distinct().forEach {
                teamsLocations[it] = mutableListOf()
            }

            for (courseName in orderedCoursesNames) {
                val courseMeetings = coursesMeetings.getValue(courseName)
                for (meeting in courseMeetings) {
                    for (team in meeting.teams) {
                        val teamLocations = teamsLocations.getValue(team)
                        teamLocations.add(meeting.getLocation())
                    }
                }
            }

            return teamsLocations
        }

        /**
         * Calculates how many teams cook more than once in the given [meetings].
         */
        private fun calculateMultipleCookingTeams(meetings: List<Meeting>): Int {
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
