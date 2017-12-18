package com.souvenotes.souvenotes.utils

import org.joda.time.DateTime

/**
 * Created on 10/15/17.
 */
object DateTimeUtils {

    private const val SAME_YEAR_FORMAT = "EEE, MMM d 'at' h:mm a"
    private const val PREV_YEAR_FORMAT = "EEE, MMM d, YY 'at' h:mm a"

    fun getDisplayFormat(timestamp: Long): String {
        val now = DateTime.now()
        val dateTime = DateTime(timestamp)
        return if (now.year > dateTime.year) {
            dateTime.toString(PREV_YEAR_FORMAT)
        } else {
            dateTime.toString(SAME_YEAR_FORMAT)
        }
    }
}