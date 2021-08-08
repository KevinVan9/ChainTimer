package com.example.chaintimer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.chaintimer.data.Datasource
import com.example.chaintimer.model.ChainTimer

class CreateActivity : AppCompatActivity() {

    // UI Elements for creating a new timer
    lateinit var hourPicker: NumberPicker
    lateinit var minutePicker: NumberPicker
    lateinit var secondPicker: NumberPicker
    lateinit var nameField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        // Setup notification channel
        createNotificationChannel()

        // Find UI elements and set parameters
        nameField = findViewById(R.id.editTimerName)
        hourPicker = findViewById(R.id.editHour)
        hourPicker.setMaxValue(99);
        hourPicker.setMinValue(0);
        minutePicker= findViewById(R.id.editMinute);
        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);
        secondPicker = findViewById(R.id.editSecond);
        secondPicker.setMaxValue(59);
        secondPicker.setMinValue(0);

        val createButton: Button = findViewById(R.id.create_button)
        createButton.setOnClickListener {
            // Get time from UI, give notification function to timer, notify adapter of new item
            // in RecyclerView so that it is displayed and move back to MainActivity
            if (getTime()!=0) {
                val timer = ChainTimer(getTime().toLong(), getName())
                timer.setNotifications(this::notify)
                Datasource.addTimer(timer)
                Datasource.adapter.notifyItemInserted(Datasource.timers.size-1 )
                finish()
            } else {
                Toast.makeText(this, R.string.zero_time_timer_toast, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Gets time selected by pickers in seconds
    fun getTime() = hourPicker.value*3600 + minutePicker.value*60 + secondPicker.value

    //Get Timer name
    fun getName(): String = if (nameField.text.toString().isBlank())
        getString(R.string.default_timer_name) else nameField.text.toString()


    /**
     * Notification stuff from documentation
     */
    var CHANNEL_ID: String = "ChainTimer"

    fun notify(id: Int, title: String, text: String) {
        // Create an explicit intent for an Activity in your app
        val intent0 = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent0, 0)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_timer)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(id, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}