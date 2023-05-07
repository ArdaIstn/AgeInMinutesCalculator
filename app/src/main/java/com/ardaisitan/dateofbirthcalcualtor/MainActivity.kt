package com.ardaisitan.dateofbirthcalcualtor

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.ardaisitan.dateofbirthcalcualtor.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedDateTextView:TextView?=null
    private var ageInMinutestv:TextView?=null
    private var ageInHourstv:TextView?=null
    private var ageInDaystv:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btnDataPicker = binding.datePickerButton
        selectedDateTextView=binding.selectedDateText
        ageInMinutestv=binding.AgeInMinutestv
        ageInHourstv=binding.AgeInHourstv
        ageInDaystv=binding.AgeInDaystv
        btnDataPicker.setOnClickListener {
            clickDatePicker()

        }

    }

    private fun clickDatePicker(){
        val myCalendar=Calendar.getInstance()
        val year=myCalendar.get(Calendar.YEAR)
        val month=myCalendar.get(Calendar.MONTH)
        val day=myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd= DatePickerDialog(this@MainActivity,{_, selectedYear,selectedMonth, selectedDayOfMonth->
            Toast.makeText(this, "Day:$selectedDayOfMonth,Month:${selectedMonth + 1},Year:$selectedYear",Toast.LENGTH_LONG).show()
            val selectedDate="$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
            selectedDateTextView?.text = selectedDate
            val sdf=SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val theDate=sdf.parse(selectedDate)


            theDate?.let {
                val selectedDateinMinutes=theDate.time/60000

                val currentDate= sdf.parse(sdf.format(System.currentTimeMillis()))//1 Ocak 1970'ten suana kadar  gecen zamani milisaniye cinsinden verecektir.
                currentDate?.let {
                    val currentDateInMinutes=currentDate.time/60000

                    val differencInMinutes=currentDateInMinutes-selectedDateinMinutes
                    ageInMinutestv?.text=differencInMinutes.toString()
                    val differenceInHours=(differencInMinutes)/60
                    ageInHourstv?.text=differenceInHours.toString()
                        val ageInDays=differenceInHours/24
                        ageInDaystv?.text=ageInDays.toString()
                        if (ageInDays>=365){
                            val realAge=ageInDays/365
                            Toast.makeText(this@MainActivity, "Your  age is $realAge", Toast.LENGTH_LONG).show()
                        }
                }

            }


        },year,month,day)
        dpd.datePicker.maxDate=System.currentTimeMillis()-86400000
        dpd.show()


    }
}

