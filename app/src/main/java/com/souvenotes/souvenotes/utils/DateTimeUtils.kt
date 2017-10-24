package com.souvenotes.souvenotes.utils

import org.joda.time.DateTime

/**
 * Created by NicholasCook on 10/15/17.
 */
object DateTimeUtils {

    fun getDisplayFormat(timestamp: Long): String {
        val dateTime = DateTime(timestamp)
        return dateTime.toString("EEE, MM d, YY hh:mm a")
    }
}