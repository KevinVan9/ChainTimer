package com.example.chaintimer.model

class ChainTimer(val seconds: Int, val name: String = "Timer") {
    override fun toString(): String {
        return "$seconds seconds"
    }
}