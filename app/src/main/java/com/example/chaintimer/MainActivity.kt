package com.example.chaintimer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chaintimer.adapter.ItemAdapter
import com.example.chaintimer.data.Datasource
import com.example.chaintimer.model.ChainTimer


/**
 * This activity allows the user to view existing timer and create a new one
 */

//TODO Add ItemTouchHelper.SimpleCallback for timer deletion and re-ordering

class MainActivity : AppCompatActivity() {

    // The view that displays all the timers in the list from DataSource
    lateinit var recyclerView: RecyclerView
    // Current timer being counted down in list
    var timerIndex = 0

    lateinit var timers: MutableList<ChainTimer>
    lateinit var pauseButton: ToggleButton
    lateinit var addButton: Button
    lateinit var resetButton: Button
    lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize list of timers
        timers = Datasource.loadTimers()

        // Setup adapter and pass it to relevant classes/objects
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = ItemAdapter(this, timers)
        recyclerView.adapter = adapter
        Datasource.adapter = adapter

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        //Attach ItemHelper
        mIth.attachToRecyclerView(recyclerView)

        // Have 'add' button transition into timer creation activity
        addButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            pauseButton.isChecked = false
            val intent = Intent(it.context, CreateActivity::class.java)
            it.context.startActivity(intent)
        }

        // Play/Pause toggle button
        //TODO complete logic for toggling with no timers or toggling after completion
        pauseButton = findViewById(R.id.toggleButton)
        pauseButton.setOnCheckedChangeListener { _, isChecked ->
            if (timers.size==0) {
                Toast.makeText(this, getString(R.string.no_timers), Toast.LENGTH_SHORT).show()
            } else if (isChecked) {
                startTimer()
            } else {
                pauseTimer()
            }
        }

        // reset button: restarts at first timer
        resetButton = findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            resetTimers()
        }

    }

    // Resumes current timer
    fun startTimer() {
        assert(timerIndex<timers.size)
        Datasource.startTimer()
        recyclerView.scrollToPosition(Datasource.timerIndex)
//        adapter.selectedPosition = Datasource.timerIndex
    }

    // Pauses current timer
    fun pauseTimer() {
        Datasource.pauseTimer()
//        if(pauseButton.isChecked) {
//            pauseButton.isChecked = false
//        }
    }

    // Reset all timers
    fun resetTimers() {
        timers.forEach{ it.reset() }
        pauseButton.isChecked = false
        Datasource.updateTimerIndex(reset = true)
    }

    // ItemTouchHelper for timer views in recycler enabling swipe to delete and press-drag to move
    var mIth = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder, target: ViewHolder
            ): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                // move item in `fromPos` to `toPos` in adapter.
                // remove from fromPos and insert back into toPos
                resetTimers()
                Datasource.moveTimer(fromPos, toPos)
                adapter.notifyItemMoved(fromPos, toPos)
                return true // true if moved, false otherwise
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                // remove from adapter
                val pos = viewHolder.adapterPosition
                pauseButton.isChecked = false
                pauseTimer()
                Datasource.removeTimer(pos)
                timerIndex = minOf(timerIndex, timers.size)
                // TODO review the logic
//                if(timers.size>0) startTimer()
                adapter.notifyItemRemoved(pos)
            }
        })

}