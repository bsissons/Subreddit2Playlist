package com.example.subreddit2playlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.view.KeyEvent
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the generate playlist button
        val thisButton: Button = findViewById(R.id.button)

        // Get the playlist object
        val playlist = GetPlaylist()

        // When the button is pressed return the playlist
        thisButton.setOnClickListener { playlist.returnPlaylist() }
        //thisButton.setOnClickListener { playlist.printLog() }
    }




}