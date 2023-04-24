package com.example.dailyplanner

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.*
import java.util.*

class EventEditActivity : AppCompatActivity() {

    private val myCalendar: Calendar = Calendar.getInstance()
    private val tz: ZoneId? = myCalendar.timeZone.toZoneId()
    private val localDate: LocalDate = LocalDateTime.ofInstant(myCalendar.toInstant(),tz).toLocalDate()
    private lateinit var eventNameET: EditText
    private lateinit var eventDescriptionET: EditText
    private lateinit var eventDateTV: TextView;
    private lateinit var eventStartTimeTV: TextView
    private lateinit var eventEndTimeTV: TextView
    private lateinit var eventStartTime: Timestamp
    private lateinit var eventEndTime: Timestamp
    private lateinit var btnEventSave: Button
    private lateinit var btnCancel: Button
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_edit)
        initWidgets()
        eventStartTime = Timestamp(myCalendar.timeInMillis)
        setupListeners()
    }

    private fun initWidgets() {
        eventNameET = findViewById(R.id.eventNameET)
        eventDateTV = findViewById(R.id.eventDateTV)
        eventStartTimeTV = findViewById(R.id.eventStartTimeTV)
        eventEndTimeTV = findViewById(R.id.eventEndTimeTV)
        eventDescriptionET = findViewById(R.id.eventDesciptionET)
        btnEventSave = findViewById(R.id.btnEventSave)
        btnCancel = findViewById(R.id.btnCancel)
    }

    @SuppressLint("SetTextI18n")
    fun setupListeners() {
        val calendarUtils = CalendarUtils()
        val date = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            eventDateTV.text = myCalendar.time.toString()
        }
        eventDateTV.setOnClickListener {
            DatePickerDialog(
                this@EventEditActivity,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        val eventStartTimePicker = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            myCalendar.set(Calendar.HOUR, hour)
            myCalendar.set(Calendar.MINUTE, minute)
            eventDateTV.text = "Date: ${calendarUtils.formattedDate(localDate)}"
            eventStartTime = Timestamp(myCalendar.timeInMillis)
        }
        eventStartTimeTV.setOnClickListener {
            TimePickerDialog(
                this@EventEditActivity,
                eventStartTimePicker,
                myCalendar.get(Calendar.HOUR),
                myCalendar.get(Calendar.MINUTE),
                true
            ).show()
        }
        val eventEndTimePicker = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            myCalendar.set(Calendar.HOUR, hour)
            myCalendar.set(Calendar.MINUTE, minute)
            eventDateTV.text = "Date: ${calendarUtils.formattedDate(localDate)}"
            eventEndTime = Timestamp(myCalendar.timeInMillis)
        }
        eventEndTimeTV.setOnClickListener {
            TimePickerDialog(
                this@EventEditActivity,
                eventEndTimePicker,
                myCalendar.get(Calendar.HOUR),
                myCalendar.get(Calendar.MINUTE),
                true
            ).show()
        }
        btnEventSave.setOnClickListener {
            saveEventAction()
            count++
            finish()
        }
        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveEventAction() {
        val eventName = eventNameET.text.toString()
        val eventDescription = eventDescriptionET.text.toString()
        val eventDetails = EventDetails(id = count, date_start = eventStartTime, date_finish = eventEndTime, name = eventName, description = eventDescription)
        eventList.add(eventDetails)
    }

}