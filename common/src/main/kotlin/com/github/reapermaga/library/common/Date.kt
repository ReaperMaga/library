package com.github.reapermaga.library.common

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

private val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

/**
 * Formats a date to a human-readable format.
 *
 * @return A string representing the date in a human-readable format. Example: 01/01/2020 12:00:00
 */
fun Date.format() : String = dateFormatter.format(this)

/**
 * Formats a long to a human-readable format.
 *
 * @return A string representing the date in a human-readable format. Example: 01/01/2020 12:00:00
 */
fun Long.timestampToFormat() : String = dateFormatter.format(Date(this))

/**
 * Formats an instant to a human-readable format.
 *
 * @return A string representing the date in a human-readable format. Example: 01/01/2020 12:00:00
 */
fun Instant.format() : String = dateFormatter.format(Date.from(this))
