package com.example.dailyplanner

import java.time.LocalTime

data class HourEvent (
    val time: LocalTime,
    val event: MutableList<EventDetails>)
