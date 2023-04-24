package com.example.dailyplanner

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

open class CalendarUtils {

    public lateinit var selectedDate: LocalDate

    open fun formattedDate(date: LocalDate): String? {
        val formatter = DateTimeFormatter.ofPattern("MMMM")
        return date.format(formatter)
    }

    open fun formattedTime(time: LocalTime): String? {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return time.format(formatter)
    }



}