package com.example.chaintimer.model

import android.os.CountDownTimer
import com.example.chaintimer.MainActivity
import com.example.chaintimer.data.Datasource
import javax.sql.DataSource

class ChainTimer(val seconds: Long, val name: String = "Timer") {
    var elapsedTime: Long = 0

    var timer = object: CountDownTimer(seconds*1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            increment()
            Datasource.updateUI()
        }
        override fun onFinish() {
            Datasource.updateTimerIndex()
        }
    }
    override fun toString(): String {
        return "$name: $elapsedTime/$seconds seconds"
    }

    fun start() {
        timer = object: CountDownTimer((seconds-elapsedTime)*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                increment()
                Datasource.updateUI()
            }
            override fun onFinish() {
                Datasource.updateTimerIndex()
            }
        }
        timer.start()
    }

    fun pause() {
        timer.cancel()
    }

    fun reset() {
        elapsedTime = 0
    }

    fun increment() {
        elapsedTime++
        Datasource.updateUI()
    }

}