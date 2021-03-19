package com.example.newsmvvm.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DateUtil {
    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun convertDate(date: String):String {
           return Instant.parse(date)                // Parse this String in standard ISO 8601 format as a `Instant`, a point on the timeline in UTC. The `Z` means UTC.
                .atOffset(ZoneOffset.UTC)                                // Change from `Instant` to the more flexible `OffsetDateTime`.
                .format(                                                   // Generate a String representing the value of this `OffsetDateTime` object.
                    DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")
                )

        }
    }
}