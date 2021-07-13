package com.example.chaintimer.data
import com.example.chaintimer.model.ChainTimer

class Datasource {
    fun loadTimers(): List<ChainTimer> {
        return listOf<ChainTimer>(
            ChainTimer(2),
            ChainTimer(4)
        )
    }
}