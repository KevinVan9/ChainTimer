package com.example.chaintimer.model

import android.os.CountDownTimer

class ChainTimer(val seconds: Long, val name: String = "Timer") {
    var remainingTime: Long = seconds

    val timer = object: CountDownTimer(seconds*1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {remainingTime--}
        override fun onFinish() { }
    }

    override fun toString(): String {
        return "$name: $remainingTime/$seconds seconds"
    }
}