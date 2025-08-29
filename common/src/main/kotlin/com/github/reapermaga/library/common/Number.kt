package com.github.reapermaga.library.common

import java.util.*

/**
 * Formats a number to a human-readable format with a thousand separator.
 *
 * @return A string representing the number in a human-readable format. Example: 1,000
 */
fun Number.formatToThousands(): String {
    return String.format("%,d", toLong())
}

fun Double.formatToDecimals(amount: Int, locale: Locale = Locale.US) = String.format(locale, "%.${amount}f", this)