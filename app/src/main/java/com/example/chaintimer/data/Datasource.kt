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
            updateUI()
            return
        }
        timerIndex++
        timerIndex = if (timerIndex>timers.size) 0 else timerIndex
    }

    fun updateUI(){
        adapter.notifyItemChanged(timerIndex)
    }
}