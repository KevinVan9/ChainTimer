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
    var timerCount = 0

    // Returns list of timers.
    // TODO initialise list from a file for timer storage and return list
    fun loadTimers(): MutableList<ChainTimer> {
        return timers
    }

    // Add a given timer to the list
    fun addTimer(timer: ChainTimer) {
        timer.id = timerCount++
        if(timers.isNotEmpty() && timers.last().completed()) {
            // Case where a timer is added after completed timers so that the current timer index
                // can be incremented
            timers.add(timer)
            updateTimerIndex()
        } else {
            timers.add(timer)
        }


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
        timerIndex = if(timerIndex>timers.size-1) timers.size-1 else  timerIndex
        adapter.selectedPosition = timerIndex
    }

    // Start next timer, updating timer index too
    fun startNextTimer() {
        updateTimerIndex()
        startTimer()
    }

    fun startTimer() {
        if(timers.size>0 && !timers[timerIndex].completed()) timers[timerIndex].start()
    }

    // Get details of next timer
    fun nextTimerDetails(): String{
        return timers[timerIndex].toString()
    }

    // update UI via adapter
    fun updateUI(all: Boolean=false){
        if (all) adapter.notifyDataSetChanged()
        else adapter.notifyItemChanged(timerIndex)
    }

    fun removeTimer(index: Int) {
        assert(index<timers.size)
        timers.removeAt(index)
        if(index in 1 until timerIndex) {
            timerIndex--
            adapter.selectedPosition = timerIndex
        }
    }

    fun moveTimer(fromPos: Int, toPos: Int) {
        assert(fromPos<timers.size && toPos<timers.size)
        var toPosShifted: Int = toPos
        if(fromPos<toPos){
            toPosShifted = toPos - 1
        }
        val item = timers[fromPos]
        timers.remove(item)
        timers.add(toPosShifted, item)
    }

    fun pauseTimer() {
        timers[timerIndex].pause()
    }
}