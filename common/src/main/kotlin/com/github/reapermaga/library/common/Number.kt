package com.github.reapermaga.library.common

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

internal val oneDecimalFormat = DecimalFormat("#.#", DecimalFormatSymbols().apply {
    decimalSeparator = ','
})

internal val twoDecimalFormat = DecimalFormat("#.##", DecimalFormatSymbols().apply {
    decimalSeparator = ','
})

/**
 * Formats a number to a human-readable format with a thousand separator.
 *
 * @return A string representing the number in a human-readable format. Example: 1,000
 */
fun Number.formatToThousands() : String {
    return String.format("%,d", toLong())
}

/**
 * Formats a number to a human-readable format with one decimal.
 *
 * @return A string representing the number in a human-readable format. Example: 1,2
 */
fun Double.formatToOneDecimal() : String {
    return oneDecimalFormat.format(this)
}

/**
 * Formats a number to a human-readable format with two decimals.
 *
 * @return A string representing the number in a human-readable format. Example: 1,23
 */
fun Double.formatToTwoDecimal() : String {
    return twoDecimalFormat.format(this)
}