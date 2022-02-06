package com.example.subreddit2playlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
//import android.view.KeyEvent
import android.widget.Button
import android.widget.Toast
import com.google.api.services.youtube.model.Playlist
//import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.*
//import kotlinx.coroutines.


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the generate playlist button
        val thisButton: Button = findViewById(R.id.button)



        // When the button is pressed return the playlist
        thisButton.setOnClickListener {
            //val thisPlaylist = GlobalScope.async { playlist.returnPlaylist() }
            //val thisPlaylist = async {getPlaylist()}.await()
            //val thisPlaylist = GlobalScope.async { getPlaylist() }
            val thisPlaylist = runBlocking {
                getPlaylist()
            }
            if (thisPlaylist == null ) {
                Toast.makeText(this, "Unable to create playlist", Toast.LENGTH_SHORT).show()
            }
        }
        //thisButton.setOnClickListener { playlist.printLog() }
    }

    private suspend fun getPlaylist() = coroutineScope {
        val newPlaylist = GetPlaylist()
        // Get the playlist object
        val thisPlaylist = async { newPlaylist.returnPlaylist() }
        thisPlaylist.await()
    }

}