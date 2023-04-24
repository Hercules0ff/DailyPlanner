package com.example.dailyplanner

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class EventDetails (
    @SerializedName("id") val id: Int,
    @SerializedName("date_start") val date_start: Timestamp,
    @SerializedName("date_finish") val date_finish: Timestamp,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String)

val eventList = mutableListOf<EventDetails>()