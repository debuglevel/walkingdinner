package de.debuglevel.walkingdinner.planner.benchmark

import org.openjdk.jmh.Main
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.sqrt


/**
 * TODO: See also https://jmh.morethan.io/
 */
open class GeoUtilsBenchmarks {
    @State(Scope.Thread)
    open class MyState {
        @Setup(Level.Invocation)
        fun doSetup() {
            a = Math.random()
            result = null
        }

//        @TearDown(Level.Invocation)
//        fun doTearDown() {
//            println("Do TearDown")
//        }

        var a: Double? = null
        var result: Double? = null
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    fun atan(state: MyState): Double {
        val a = state.a!!

        val c = atan2(sqrt(a), sqrt(1 - a))
        state.result = c

        return c
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    fun asin(state: MyState): Double {
        val a = state.a!!

        val c = asin(a)
        state.result = c

        return c
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

    object BenchmarkRunner {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            Main.main(args)
        }
    }
}