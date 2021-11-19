package de.debuglevel.walkingdinner.planner.planner.geneticplanner

import de.debuglevel.walkingdinner.planner.Courses
import de.debuglevel.walkingdinner.planner.planner.FitnessCalculator
import de.debuglevel.walkingdinner.planner.team.Team
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
            FitnessCalculator.getFitness(courses)
        }
    }
}
