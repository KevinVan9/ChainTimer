package com.example.chaintimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.chaintimer.adapter.ItemAdapter
import com.example.chaintimer.data.Datasource
import com.example.chaintimer.model.ChainTimer
import java.util.*

/**
 * This activity allows the user to view existing timer and create a new one
 */

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    var timerIndex = 0
    var paused: Boolean = true
    lateinit var timers: MutableList<ChainTimer>
    lateinit var pauseButton: ToggleButton
    lateinit var addButton: Button
    lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize list of timers
        timers = Datasource.loadTimers()

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = ItemAdapter(this, timers)
        recyclerView.adapter = adapter
        Datasource.adapter = adapter

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        // Have 'add' button transition into timer creation activity
        addButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(it.context, CreateActivity::class.java)
//            context.startActivity(intent)
            // auto add for Testing
            Datasource.addTimer(ChainTimer(4))
            adapter.notifyItemInserted(Datasource.timers.size - 1)
        }

        // Play/Pause toggle button
        //TODO complete logic for toggling with no timers or toggling after completion
        pauseButton = findViewById(R.id.toggleButton)
        pauseButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                startTimer()
            } else {
                pauseTimer()
            }
        }

        // reset button: restarts at first timer
        resetButton = findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            resetTimers()
        }

    }

    // Resumes current timer
    fun startTimer() {
        timers[timerIndex].start()
        recyclerView.scrollToPosition(timerIndex)
        Datasource.adapter.selectedPosition = timerIndex
    }

    // Pauses current timer
    fun pauseTimer() {
        timers[timerIndex].pause()
    }

    // Reset all timers
    fun resetTimers() {
        timers.forEach{ it.reset() }
        Datasource.updateTimerIndex(reset = true)
        timerIndex = 0
        pauseButton.isChecked = false
    }

    // Transition to next timer
    fun nextTimer() {
        timerIndex++
        startTimer()
    }


}