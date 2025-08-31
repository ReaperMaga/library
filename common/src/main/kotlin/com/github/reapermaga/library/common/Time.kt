package com.github.reapermaga.library.common

import java.util.*
import java.util.concurrent.TimeUnit

fun Number.toPaddedTimeString(
    unit: TimeUnit = TimeUnit.MILLISECONDS,
    padding: Int = 2,
): String {
    val totalSeconds = unit.toSeconds(this.toLong())
    val units = MutableList(padding) { 0L }
    var remainder = totalSeconds
    for (i in padding - 1 downTo 1) {
        units[i] = remainder % 60
        remainder /= 60
    }
    units[0] = remainder
    return units.joinToString(":") { String.format("%02d", it) }
}

fun Long.toHumanReadableDuration(timeUnit: TimeUnit): String {
    val totalSeconds = timeUnit.toSeconds(this)
    val months = totalSeconds / (30 * 24 * 3600)
    val weeks = (totalSeconds % (30 * 24 * 3600)) / (7 * 24 * 3600)
    val days = (totalSeconds % (7 * 24 * 3600)) / (24 * 3600)
    val hours = (totalSeconds % (24 * 3600)) / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return buildString {
        if (months > 0) append("${months}mo ")
        if (weeks > 0) append("${weeks}w ")
        if (days > 0) append("${days}d ")
        if (hours > 0) append("${hours}h ")
        if (minutes > 0) append("${minutes}m ")
        if (seconds >= 0) append("${seconds}s")
    }.trim()
}

fun Number.formatTimeToDecimals(
    unit: TimeUnit,
    amount: Int,
    locale: Locale = Locale.US,
): String {
    val seconds =
        when (unit) {
            TimeUnit.SECONDS -> this.toDouble()
            TimeUnit.MINUTES -> this.toDouble() * 60
            TimeUnit.HOURS -> this.toDouble() * 3600
            TimeUnit.DAYS -> this.toDouble() * 86400
            TimeUnit.MILLISECONDS -> this.toDouble() / 1000
            TimeUnit.MICROSECONDS -> this.toDouble() / 1_000_000
            TimeUnit.NANOSECONDS -> this.toDouble() / 1_000_000_000
        }
    return String.format(locale, "%.${amount}f", seconds)
}
