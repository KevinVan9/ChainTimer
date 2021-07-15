package com.example.chaintimer.data
import com.example.chaintimer.adapter.ItemAdapter
import com.example.chaintimer.model.ChainTimer

object Datasource {
    val timers: MutableList<ChainTimer> = mutableListOf<ChainTimer>(ChainTimer(3, "Default Timer"));
    lateinit var adapter: ItemAdapter
    var timerIndex = 0

    fun loadTimers(): MutableList<ChainTimer> {
        return timers
    }

    fun addTimer(timer: ChainTimer) {
        timers.add(timer)
    }

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

    fun nextTimer() {
        updateTimerIndex()
        if(timerIndex!=0) timers[timerIndex].start()
    }

    fun updateUI(all: Boolean=true){
        if (all) adapter.notifyDataSetChanged()
        else adapter.notifyItemChanged(timerIndex)
    }
}