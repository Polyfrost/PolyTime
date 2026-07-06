package org.polyfrost.polytime

import java.util.*

val irlTime: Float
    get() {
        val calendar: Calendar = Calendar.getInstance()
        return calendar.get(Calendar.HOUR_OF_DAY) +
                calendar.get(Calendar.MINUTE) / 60f +
                calendar.get(Calendar.SECOND) / 3600f +
                calendar.get(Calendar.MILLISECOND) / 3600000f
    }

fun Float.isWithinPeriod(periodStart: Float, periodEnd: Float): Boolean {
    var current = this
    var end = periodEnd
    if (end < periodStart) {
        end += 24f
        if (current < periodStart) {
            current += 24f
        }
    }

    return current in periodStart..end
}

fun Float.realTimeToGameTime(): Long {
    return (this * 1_000L).toLong() + 18_000L
}

fun map(x: Float, inMin: Float, inMax: Float, outMin: Float, outMax: Float): Float {
    return (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin
}
