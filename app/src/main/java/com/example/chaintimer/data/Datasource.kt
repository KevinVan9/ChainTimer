package com.example.chaintimer.data
import com.example.chaintimer.MainActivity
import com.example.chaintimer.adapter.ItemAdapter
import com.example.chaintimer.model.ChainTimer

object Datasource {

    // Storage of timer objects in list
    val timers: MutableList<ChainTimer> = mutableListOf<ChainTimer>()
    // Current active timer in list
    var timerIndex = 0
    // adapter set by activity so that adapter selectedPosition can be set. Could just access Datasource timerIndex from ItemAdapter class
    lateinit var adapter: ItemAdapter

    // Returns list of timers.
    // TODO initialise list from a file for timer storage and return list
    fun loadTimers(): MutableList<ChainTimer> {
        return timers
    }

    // Add a given timer to the list
    fun addTimer(timer: ChainTimer) {
        timers.add(timer)
        timer.id = timerIndex
    }

    // Update timer index to the next timer, looping back to start if at the end of list
    fun updateTimerIndex(reset: Boolean = false){
        if(reset) {
            timerIndex = 0
            adapter.selectedPosition = 0
            updateUI(all = true)
            return
        }
        timerIndex++
        timerIndex = if (timerIndex>=timers.size) 0 else timerIndex
        adapter.selectedPosition = timerIndex
    }

    // Start next timer, updating timer index too
    fun startNextTimer() {
        updateTimerIndex()
        if(timerIndex!=0) timers[timerIndex].start()
    }

    // Get details of next timer
    fun nextTimerDetails(): String{
        return timers[timerIndex].toString()
    }

    // update UI via adapter
    fun updateUI(all: Boolean=true){
        if (all) adapter.notifyDataSetChanged()
        else adapter.notifyItemChanged(timerIndex)
    }
}