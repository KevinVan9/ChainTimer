package com.example.chaintimer.data
import com.example.chaintimer.model.ChainTimer

class Datasource {
    val timers: MutableList<ChainTimer> = mutableListOf<ChainTimer>(ChainTimer(3));

    fun loadTimers(): MutableList<ChainTimer> {
        return timers
    }

    fun addTimer(timer: ChainTimer) {
        timers.add(timer)
        println("Created new timer: $timer")
    }
}