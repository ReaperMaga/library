package dev.reapermaga.library.common

fun Number.secondsTo2Digits(): String {
    val seconds = this.toLong()
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

fun Number.secondsTo3Digits(): String {
    val seconds = this.toLong()
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}

fun Long.millisTo2Digits(): String {
    return (this / 1000).secondsTo2Digits()
}

fun Long.millisTo3Digits(): String {
    return (this / 1000).secondsTo3Digits()
}

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
        if (seconds > 0) append("${seconds}sec")
    }.trim()
}