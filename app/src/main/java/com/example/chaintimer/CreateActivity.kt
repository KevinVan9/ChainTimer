package com.example.chaintimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import androidx.recyclerview.widget.RecyclerView
import com.example.chaintimer.data.Datasource
import com.example.chaintimer.model.ChainTimer

class CreateActivity : AppCompatActivity() {

    lateinit var hourPicker: NumberPicker
    lateinit var minutePicker: NumberPicker
    lateinit var secondPicker: NumberPicker
    lateinit var nameField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        nameField = findViewById(R.id.editTimerName)
        hourPicker = findViewById(R.id.editHour)
        hourPicker.setMaxValue(99);
        hourPicker.setMinValue(0);
        minutePicker= findViewById(R.id.editMinute);
        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);
        secondPicker = findViewById(R.id.editSecond);
        secondPicker.setMaxValue(59);
        secondPicker.setMinValue(0);

        val createButton: Button = findViewById(R.id.create_button)
        createButton.setOnClickListener {
            if (getTime()!=0) {
                Datasource.addTimer(ChainTimer(getTime().toLong(), getName()))
                Datasource.adapter.notifyItemInserted(Datasource.timers.size - 1)
                val context = it.context
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    //Gets time selected by pickers in seconds
    fun getTime() = hourPicker.value*3600 + minutePicker.value*60 + secondPicker.value

    //Get Timer name
    fun getName(): String = if (nameField.text.toString().isBlank()) "Timer" else nameField.text.toString()
}