package de.debuglevel.walkingdinner.planner.benchmark

import de.debuglevel.walkingdinner.planner.Location
import de.debuglevel.walkingdinner.planner.common.GeoUtils
import org.openjdk.jmh.Main
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


/**
 * TODO: See also https://jmh.morethan.io/
 */
open class GeoUtilsBenchmarks {
//    @State(Scope.Thread)
//    open class MyState {
//        @Setup(Level.Invocation)
//        fun doSetup() {
//            a = Math.random()
//            result = null
//        }
//
////        @TearDown(Level.Invocation)
////        fun doTearDown() {
////            println("Do TearDown")
////        }
//
//        var a: Double? = null
//        var result: Double? = null
//    }
//
//    @Benchmark
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    fun atan(state: MyState): Double {
//        val a = state.a!!
//
//        val c = atan2(sqrt(a), sqrt(1 - a))
//        state.result = c
//
//        return c
//    }
//
//    @Benchmark
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    fun asin(state: MyState): Double {
//        val a = state.a!!
//
//        val c = asin(a)
//        state.result = c
//
//        return c
//    }


    @State(Scope.Thread)
    open class MyState {
        @Setup(Level.Invocation)
        fun doSetup() {
            source = pseudorandomLocation()
            destination = pseudorandomLocation()
        }

        var source: Location? = null
        var destination: Location? = null
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(2)
    @Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
    fun cached(state: MyState): Double {
        return GeoUtils.cachedCalculateDistance(state.source!!, state.destination!!)
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(2)
    @Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
    fun uncached(state: MyState): Double {
        return GeoUtils.calculateDistance(state.source!!, state.destination!!)
    }

//    @Param("asin", "atan")
//    val name: String = ""
//
//    @Benchmark
//    open fun bench(): Double {
//        return when (name) {
//            "asin" -> asin()
//            "atan" -> atan()
//            else -> throw Exception()
//        }
//    }

    companion object {
        fun randomLongitude(): Double {
            return Random.nextDouble(-180.0, +180.0)
        }

        fun randomLatitude(): Double {
            return Random.nextDouble(-90.0, +90.0)
        }

        /**
         * Returns a random longitude of a predefined set.
         */
        fun pseudorandomLongitude(): Double {
            return setOf<Double>(
                -180.0, 180.0, 0.0, 90.0, -90.0,
                23.2344, 54.234543, 123.2345, -23.757, -63.54646, -1.5454
            ).random()
        }

        /**
         * Returns a random latitude of a predefined set.
         */
        fun pseudorandomLatitude(): Double {
            return setOf<Double>(
                0.0, 90.0, -90.0,
                23.2344, 54.234543, 123.2345, -23.757, -63.54646, -1.5454, 76.465, -24.43569
            ).random()
        }

        fun pseudorandomLocation(): Location {
            return setOf(
                Location(-180.0, 56.54665),
                Location(0.0, -7.3543),
                Location(180.0, 26.3333),
                Location(90.0, 5.5555),
                Location(-90.0, -6.547),
                Location(123.44, 90.0),
                Location(-24.435345, -90.0),
                Location(43.5466543, 0.0),
                Location(0.12343, 23.643),
                Location(-1.32243, 1.3443),
                Location(47.54543, -85.5343),
                Location(85.234243, 58.2342),
                Location(-78.2344, -1.234234),
                Location(7.23424, 66.243523),
            ).random()
        }
    }

    object BenchmarkRunner {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            Main.main(args)
        }
    }
}