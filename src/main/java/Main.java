import io.jenetics.*;
import io.jenetics.engine.*;
import io.jenetics.util.ISeq;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    private Database database;

//    // 2.) Definition of the fitness function.
//    private static int eval(Genotype<BitGene> gt) {
//        return gt.getChromosome()
//                .as(BitChromosome.class)
//                .bitCount();
//    }

    public static void main(String[] args) {
        new Main().run();
    }

    private int eval(Genotype<IntegerGene> gt) {

        int teamCount = this.database.getTeams().size();

        int[] course1 = new int[teamCount];
//        int[] course2 = new int[teamCount];
//        int[] course3 = new int[teamCount];

        chromosomeToArrays(gt, teamCount, course1 /*, course2, course3*/);

        int invalidGenotypeMalus = 0;
        if (isValidCourse(course1) == false) {
            invalidGenotypeMalus -= 5;
        }
//        if (isValidCourse(course2) == false)
//        {
//            invalidGenotypeMalus -= 5;
//        }if (isValidCourse(course3) == false)
//        {
//            invalidGenotypeMalus -= 5;
//        }


        return evaluateCourse(course1, "Vorspeise") //+evaluateCourse(course2, "Hauptgericht")+evaluateCourse(course3, "Dessert")
                + invalidGenotypeMalus;
    }

    private void chromosomeToArrays(Genotype<IntegerGene> gt, int teamCount, int[] course1 /*, int[] course2, int[] course3*/) {
        IntegerChromosome chromosome = gt.getChromosome()
                .as(IntegerChromosome.class);
        int[] chromosomeArray = chromosome.toArray();

        System.arraycopy(chromosomeArray, 0 * teamCount, course1, 0, teamCount);
//        System.arraycopy(chromosomeArray, 1*teamCount, course2, 0, teamCount);
//        System.arraycopy(chromosomeArray, 2*teamCount, course3, 0, teamCount);
    }

    private boolean isValidGenotype(Genotype<IntegerGene> gt) {
        int teamCount = this.database.getTeams().size();

        int[] course1 = new int[teamCount];
        int[] course2 = new int[teamCount];
        int[] course3 = new int[teamCount];

        chromosomeToArrays(gt, teamCount, course1 /*, course2, course3*/);

        return isValidCourse(course1) && isValidCourse(course2) && isValidCourse(course3);
    }

    private boolean isValidCourse(int[] course) {


        for (int idxTeam = 0; idxTeam < course.length; idxTeam++) {
            int finalIdxTeam = idxTeam;
            boolean teamAvailable = Arrays.stream(course).anyMatch(c -> c == finalIdxTeam);

            if (teamAvailable == false) {
                //System.out.println("invalid: "+result);
                return false;
            }
        }

        String result = Arrays.stream(course)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" "));

        System.out.println("valid:   " + result);
        return true;
    }

//    private void print(Genotype<IntegerGene> gt)
//    {
//        int teamCount = this.database.getTeams().size();
//
//        int[] course1 = new int[teamCount];
////        int[] course2 = new int[teamCount];
////        int[] course3 = new int[teamCount];
//
//        chromosomeToArrays(gt, teamCount, course1 /*, course2, course3*/);
//
//        printCourse(course1, "Vorspeise");
////        printCourse(course2, "Hauptgericht");
////        printCourse(course3, "Nachspeise");
//    }

    private void print(Genotype<EnumGene<Team>> gt) {

        for (int idxChromosome = 0; idxChromosome < 3; idxChromosome++) {
            System.out.println("== Chromosom "+idxChromosome);

            for (int i = 0; i < gt.getChromosome(idxChromosome).length(); i++) {
                if (i % 3 == 0) {
                    System.out.println();
                }

                System.out.print(gt.getChromosome(idxChromosome).getGene(i) + " ");
            }

            System.out.println();
        }



//        int teamCount = this.database.getTeams().size();
//
//        int[] course1 = new int[teamCount];
////        int[] course2 = new int[teamCount];
////        int[] course3 = new int[teamCount];
//
//        chromosomeToArrays(gt, teamCount, course1 /*, course2, course3*/);
//
//        printCourse(course1, "Vorspeise");
////        printCourse(course2, "Hauptgericht");
////        printCourse(course3, "Nachspeise");
    }

    private void printCourse(int[] course, String name) {
//        System.out.println(name);
        int meetingsCount = course.length / 3;
        for (int idxMeeting = 0; idxMeeting < meetingsCount; idxMeeting++) {
            int idxTeam1 = course[idxMeeting * 3];
            int idxTeam2 = course[idxMeeting * 3 + 1];
            int idxTeam3 = course[idxMeeting * 3 + 2];
            Team team1 = this.database.getTeams().get(idxTeam1);
            Team team2 = this.database.getTeams().get(idxTeam2);
            Team team3 = this.database.getTeams().get(idxTeam3);
            Team[] teams = {team1, team2, team3};

            Meeting meeting = new Meeting(teams, name);
            System.out.println(meeting);
        }

        System.out.println();
    }

//    private long calculateFitness()
//    {
//        // do all meeting have compatible teams?
//        // does every team have three different meetings with different courses?
//    }

    private int evaluateCourse(int[] course, String name) {
        int incompatibleTeams = 0;

        int meetingsCount = course.length / 3;
        for (int idxMeeting = 0; idxMeeting < meetingsCount; idxMeeting++) {
            int idxTeam1 = course[idxMeeting * 3];
            int idxTeam2 = course[idxMeeting * 3 + 1];
            int idxTeam3 = course[idxMeeting * 3 + 2];
            Team team1 = this.database.getTeams().get(idxTeam1);
            Team team2 = this.database.getTeams().get(idxTeam2);
            Team team3 = this.database.getTeams().get(idxTeam3);
            Team[] teams = {team1, team2, team3};

            Meeting meeting = new Meeting(teams, name);
            if (meeting.areCompatibleTeams() == false) {
                incompatibleTeams++;
            }
        }

        return incompatibleTeams * -1;
    }

    void initialize() {
        this.database = new Database();
        this.database.initializeTeams();
    }

    void printDatabase() {
        for (int i = 0; i < this.database.getTeams().size(); i++) {
            System.out.println(this.database.getTeams().get(i));
        }
    }

    void run() {
        this.initialize();
//        this.printDatabase();
        this.compute();
    }


    void compute() {

        //Genotype.of()

        // 1.) Define the genotype (factory) suitable
        //     for the problem.

//         int courses = 3;
//         int length = database.getTeams().size()*courses;
//        Factory<Genotype<IntegerGene>> genotypeFactory =
//                Genotype.of(IntegerChromosome.of(0, database.getTeams().size()-1, length));

        CoursesProblem problem = new CoursesProblem(getTeams());

        // 3.) Create the execution environment.
        Engine<EnumGene<Team>, Double> engine = Engine
                .builder(problem)
                .optimize(Optimize.MINIMUM)
//                .populationSize(1000)
//                .alterers(new SinglePointCrossover<>(1), new Mutator<>(0.01))
                .alterers(new SwapMutator<>(0.15),
                        new PartiallyMatchedCrossover<>(0.15))
//                .selector(new RouletteWheelSelector<>())
//                .genotypeValidator(this::isValidGenotype)
                .build();


        // Create evolution statistics consumer.
        final EvolutionStatistics<Double, ?>
                statistics = EvolutionStatistics.ofNumber();

        final EvolutionResult<EnumGene<Team>, Double> result = engine.stream()
//                .limit(1000)
                .limit(Limits.bySteadyFitness(10000))
//                .limit(Limits.byFitnessThreshold(0.5d))
                 .peek(g -> {
                     if (g.getGeneration() % 500 == 0) {
                         System.out.println("Generation: " + g.getGeneration() + "\t| Best Fitness: " + g.getBestFitness());
                     }
                 })
                .peek(statistics)
                .collect(EvolutionResult.toBestEvolutionResult());

//        // 4.) Start the execution (evolution) and
//        //     collect the result.
//         EvolutionResult<IntegerGene, Integer> result = engine.stream()
//                .limit(Limits.bySteadyFitness(500))
//                .peek(g -> {
//                    if (g.getGeneration() % 100 == 1) {
//                        System.out.println("Generation: " + g.getGeneration() + "\t| Best Fitness: " + g.getBestFitness());
//                    }
//                })
//                .collect(EvolutionResult.toBestEvolutionResult());

        print(result.getBestPhenotype().getGenotype());

        /*
         * print the results
         */
        System.out.println();
        System.out.println("-----------------");
        System.out.println("Best in Generation: " + result.getGeneration());
        System.out.println("Best with Fitness: " + result.getBestFitness());

        System.out.println(statistics);
    }

//    private boolean byFitnessThreshold(EvolutionResult<EnumGene<Team>, Double> enumGeneDoubleEvolutionResult) {
//        return true;
//    }


    private ISeq<Team> getTeams() {
        return ISeq.of(this.database.getTeams());
    }

}