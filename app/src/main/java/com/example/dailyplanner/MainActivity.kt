package com.example.dailyplanner
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val myCalendar: Calendar = Calendar.getInstance()
    private val tz: ZoneId? = myCalendar.timeZone.toZoneId()
    private val localDate: LocalDate = LocalDateTime.ofInstant(myCalendar.toInstant(),tz).toLocalDate()
    private lateinit var daySelector: TextView
    private lateinit var monthSelector: TextView
    private lateinit var yearSelector: TextView
    private lateinit var btnNewEvent: Button
    private lateinit var hourListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        setHourAdapter()
    }

    private fun setHourAdapter() {
        val hourAdapter = HourAdapter(applicationContext, hourEventList())
        hourListView.adapter = hourAdapter
    }

    private fun hourEventList(): MutableList<HourEvent> {
        val list = mutableListOf<HourEvent>()

        for (hour in 0 until  24) {
            val time = LocalTime.of(hour, 0)
            val events = eventList
            val hourEvent = HourEvent(time, events)
            list.add(hourEvent)
        }

        return list
    }

    private fun setupListeners() {
        val date = OnDateSetListener { _, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateDate(daySelector, monthSelector, yearSelector)
            }
        daySelector.setOnClickListener {
            DatePickerDialog(
                this@MainActivity,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        btnNewEvent.setOnClickListener {
            newEventAction()
        }

    }

    private fun initWidgets() {
        daySelector = findViewById(R.id.daySelector)
        monthSelector = findViewById(R.id.monthSelector)
        yearSelector = findViewById(R.id.yearSelector)
        btnNewEvent = findViewById(R.id.btnNewEvent)
        hourListView = findViewById(R.id.hourListView)
    }

    fun updateDate(_daySelector: TextView, _monthSelector: TextView, _yearSelector: TextView){
        val calendarUtils = CalendarUtils()
        _daySelector.text = myCalendar.get(Calendar.DAY_OF_MONTH).toString()
        _monthSelector.text = calendarUtils.formattedDate(localDate)
        _yearSelector.text = myCalendar.get(Calendar.YEAR).toString()
    }

    fun newEventAction() {
        startActivity(Intent(this, EventEditActivity::class.java))
    }

}