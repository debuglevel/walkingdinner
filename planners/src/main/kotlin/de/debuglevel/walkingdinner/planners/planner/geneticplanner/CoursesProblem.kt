package de.debuglevel.walkingdinner.planners.planner.geneticplanner

import de.debuglevel.walkingdinner.planners.Courses
import de.debuglevel.walkingdinner.planners.Location
import de.debuglevel.walkingdinner.planners.Meeting
import de.debuglevel.walkingdinner.planners.Team
import de.debuglevel.walkingdinner.planners.common.GeoUtils
import de.debuglevel.walkingdinner.planners.dietcompatibility.CourseDietCompatibility
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
                .flatMap { cm -> cm.value.stream() }
                .collect(Collectors.toSet<Meeting>())

            val overallDistanceMalus = 0.00001 * calculateOverallDistance(courses)
            val incompatibleTeamsCourse1Malus = 1 * calculateIncompatibleTeams(
                courseMeetings.getValue(Courses.course1name)
            )
            val incompatibleTeamsCourse2Malus = 1 * calculateIncompatibleTeams(
                courseMeetings.getValue(Courses.course2name)
            )
            val incompatibleTeamsCourse3Malus = 1 * calculateIncompatibleTeams(
                courseMeetings.getValue(Courses.course3name)
            )
            val multipleCookingTeamsMalus = 1 * calculateMultipleCookingTeams(
                meetings
            )

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
        private fun calculateLocationsDistance(locations: List<Location?>): Double {
            val location0 = locations[0]!!
            val location1 = locations[1]!!
            val location2 = locations[2]!!

            return GeoUtils.calculateDistanceInKilometer(location0, location1) +
                    GeoUtils.calculateDistanceInKilometer(location1, location2)
        }

        private fun getTeamLocations(courseMeetings: Map<String, List<Meeting>>): HashMap<Team, MutableList<Location?>> {
            val teamsLocations = HashMap<Team, MutableList<Location?>>()

            addLocations(
                teamsLocations,
                courseMeetings[Courses.course1name]
            )
            addLocations(
                teamsLocations,
                courseMeetings[Courses.course2name]
            )
            addLocations(
                teamsLocations,
                courseMeetings[Courses.course3name]
            )

            return teamsLocations
        }

        fun calculateIncompatibleTeams(meetings: Set<Meeting>): Double {
            return meetings.stream()
                //                .filter(m -> !HardCompatibility.INSTANCE.areCompatibleTeams(m))
                .filter { m -> !CourseDietCompatibility.areCompatibleTeams(m) }
                .count()
                .toDouble()
        }

        fun calculateOverallDistance(courses: Courses): Double {
            val meetings = courses.toMeetings()

            val courseMeetings = meetings.groupBy { it.course }

            val teamsLocations =
                getTeamLocations(
                    courseMeetings
                )

            return teamsLocations.values
                .map {
                    calculateLocationsDistance(
                        it
                    )
                }
                .sum()
        }

        private fun addLocations(teamsLocations: HashMap<Team, MutableList<Location?>>, meetings: List<Meeting>?) {
            if (meetings != null) {
                for (meeting in meetings) {
                    for (team in meeting.teams) {
                        // get item in HashMap or create empty List if not already available
                        val teamLocations =
                            teamsLocations.computeIfAbsent(team) {
                                mutableListOf()
                            }

                        teamLocations.add(meeting.getCookingTeam().location)
                    }
                }
            }
        }

        private fun calculateMultipleCookingTeams(meetings: Set<Meeting>): Double {
            val teamCookings = meetings.map { it.getCookingTeam() }
                .groupBy { it }
                .mapValues { it.value.count() }

            val countMultipleCookingTeams = teamCookings.entries
                .filter { kv -> kv.value > 1 }
                .map { it.value }
                .sum()

            return countMultipleCookingTeams.toDouble()
        }
    }
}
