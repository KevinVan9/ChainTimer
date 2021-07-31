package com.example.chaintimer.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chaintimer.R
import com.example.chaintimer.model.ChainTimer

/**
 * Adapter for the [RecyclerView] in [MainActivity]. Displays [ChainTimer] data object.
 */

class ItemAdapter(private val context: Context,
                  private val dataset: List<ChainTimer>
                  ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // Variable holding index of current timer in Datasource list
    var selectedPosition: Int = 0

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.editTimerName)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        // Update text and progress bar in UI
        holder.textView.text = item.toString()
        holder.progressBar.progress = (100*item.elapsedTime/item.seconds).toInt()
        // Highlight current timer
        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(Color.parseColor(context.getString(R.string.highlight_colour)));
        else
            holder.itemView.setBackgroundColor(Color.parseColor(context.getString(R.string.no_highlight_colour)));
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */

    override fun getItemCount() = dataset.size

}