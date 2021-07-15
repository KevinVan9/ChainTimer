package com.example.chaintimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.chaintimer.adapter.ItemAdapter
import com.example.chaintimer.data.Datasource
import com.example.chaintimer.model.ChainTimer
import java.util.*

/**
 * This activity allows the user to view existing timer and create a new one
 */

class MainActivity : AppCompatActivity() {

    private val startTime: Long = Calendar.getInstance().timeInMillis

    var timerIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize data.
        val myDataset = Datasource.loadTimers()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = ItemAdapter(this, myDataset)
        recyclerView.adapter = adapter
        Datasource.adapter = adapter

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val context = it.context
            val intent = Intent(context, CreateActivity::class.java)
            context.startActivity(intent)
        }


    }
}