package com.github.reapermaga.library.common


/**
 * Converts seconds to a human-readable format.
 *
 * @return A string representing the time in a human-readable format. Example: 02:10
 */
fun Number.secondsTo2Digits(): String {
    val seconds = this.toLong()
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

/**
 * Converts seconds to a human-readable format.
 *
 * @return A string representing the time in a human-readable format. Example: 02:10:05
 */
fun Number.secondsTo3Digits(): String {
    val seconds = this.toLong()
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}

/**
 * Converts milliseconds to a human-readable format.
 *
 * @return A string representing the time in a human-readable format. Example: 05:01
 */
fun Long.millisTo2Digits(): String {
    return (this / 1000).secondsTo2Digits()
}

/**
 * Converts milliseconds to a human-readable format.
 *
 * @return A string representing the time in a human-readable format. Example: 02:10:05
 */
fun Long.millisTo3Digits(): String {
    return (this / 1000).secondsTo3Digits()
}

/**
 * Converts milliseconds to a human-readable format.
 *
 * @return A string representing the time in a human-readable format. Example: 1m 10s
 */
fun Long.millisToTimeFormat(): String {
    val totalSeconds = this / 1000
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

/**
 * Converts seconds to a human-readable format.
 *
 * @return A string representing the time in a human-readable format. Example: 1m 10s
 */
fun Number.secondsToTimeFormat(): String {
    return (this.toLong()*1000).millisToTimeFormat()
}

/**
 * Converts milliseconds to seconds show it as decimal format
 *
 * @return A string representing the time in decimals. Example: 1.5
 */
fun Long.millisToSecondsWithOneDecimal(): String {
    return oneDecimalFormat.format(this / 1000.0)
}
