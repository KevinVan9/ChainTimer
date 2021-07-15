package com.example.chaintimer.data
import com.example.chaintimer.adapter.ItemAdapter
import com.example.chaintimer.model.ChainTimer

object Datasource {
    val timers: MutableList<ChainTimer> = mutableListOf<ChainTimer>(ChainTimer(3, "Default Timer"));
    lateinit var adapter: ItemAdapter

    fun loadTimers(): MutableList<ChainTimer> {
        return timers
    }

    fun addTimer(timer: ChainTimer) {
        timers.add(timer)
    }
}