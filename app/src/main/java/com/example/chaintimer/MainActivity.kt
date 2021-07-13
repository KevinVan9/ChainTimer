package com.example.chaintimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.chaintimer.adapter.ItemAdapter
import com.example.chaintimer.data.Datasource
import com.example.chaintimer.model.ChainTimer

/**
 * This activity allows the user to view existing timer and create a new one
 */

class MainActivity : AppCompatActivity() {

//    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize data.
        val data = Datasource()
        val myDataset = data.loadTimers()
        for(item in myDataset) {
            println(item.toString())
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = ItemAdapter(this, myDataset)
        recyclerView.adapter = adapter

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        val createButton: Button = findViewById(R.id.button)
        createButton.setOnClickListener {
            data.addTimer(ChainTimer(3))
            adapter.notifyItemInserted(data.timers.size-1)
        }
    }
}