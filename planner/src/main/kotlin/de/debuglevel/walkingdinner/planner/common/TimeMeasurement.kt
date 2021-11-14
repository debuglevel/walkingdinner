package de.debuglevel.walkingdinner.planner.common

import mu.KotlinLogging
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.roundToInt

object TimeMeasurement {
    private val logger = KotlinLogging.logger {}

    private val measurements = mutableMapOf<Any, Measurement>()

    /**
     * Add a time measurement ([nanosecondsDuration]) of an executed operation (identified by an [id]). Print a report on every [reportInterval] calls.
     * Remarks:
     * - Use it only on a single thread (results may be not 100% accurate with multiple threads)
     * - Only measure one variant of a function; replace it afterwards and recompile and rerun (subsequent calls are faster than the first one)
     */
    fun add(
        id: Any,
        nanosecondsDuration: Long,
        reportInterval: Long
    ) {
        val measurement = measurements.getOrPut(id) { Measurement(id) }

        // Does not lock both variables (and probably also should not as it would block at least for a short time); strictly-speaking is not accurate with multiple threads.
        val callsSum = measurement.calls.incrementAndGet()
        val nanosecondsSum = measurement.nanoseconds.addAndGet(nanosecondsDuration)

        if (callsSum.rem(reportInterval) == 0L) {
            val durationPerCall = nanosecondsSum / callsSum
            val callsPerSecond = (callsSum / (nanosecondsSum / 1_000_000_000.0)).roundToInt()

            println("Performance of '${measurement.id}' after $callsSum calls: $durationPerCall ns/call or $callsPerSecond calls/s")
        }
    }

    data class Measurement(val id: Any) {
        var calls = AtomicLong(0)
        var nanoseconds = AtomicLong(0)
    }
}