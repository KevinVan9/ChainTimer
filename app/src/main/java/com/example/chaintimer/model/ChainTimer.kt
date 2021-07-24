package com.example.chaintimer.model

import android.os.CountDownTimer
import com.example.chaintimer.MainActivity
import com.example.chaintimer.data.Datasource

class ChainTimer(val seconds: Long, val name: String = "Timer") {

    // Stores time elapsed for current timer so that it can be paused and restarted
    var elapsedTime: Long = 0
    // id variable so that timers in list can be uniquely identified
    var id: Int = -1

    // initial timer
    var timer = object: CountDownTimer(seconds*1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            increment()
            Datasource.updateUI()
        }
        override fun onFinish() {
            notification_fun(id, "$name: $seconds seconds completed", "${Datasource.nextTimerDetails()}")
            Datasource.startNextTimer()
        }
    }

    override fun toString(): String {
        return "$name: $elapsedTime/$seconds seconds"
    }

    // Starts timer given the elapsed time
    fun start() {
        timer = object: CountDownTimer((seconds-elapsedTime)*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                increment()
                Datasource.updateUI()
            }
            override fun onFinish() {
                notification_fun(id, "$name: $seconds seconds completed", "${Datasource.nextTimerDetails()}")
                Datasource.startNextTimer()
            }
        }
        timer.start()
    }

    // Stop the timer
    fun pause() {
        timer.cancel()
    }

    // Reset the elapsed time and stop the timer
    fun reset() {
        elapsedTime = 0
        timer.cancel()
    }

    // Increment the elapsed time and update the GUI
    fun increment() {
        elapsedTime++
        Datasource.updateUI()
    }

    /**
     * Notification function to be set by CreateActivity so that the timer can call the notification
     * function defined in the activity. I only read a bit of the documentation, didn't really
     * understand and have never done android or mobile programming before.
     * Couldn't place the notification code in this class
     */

    lateinit var notification_fun: (Int, String, String) -> Unit
    fun setNotifications(notify: (id: Int, title: String, content: String) -> Unit) {
        this.notification_fun = notify
    }


}