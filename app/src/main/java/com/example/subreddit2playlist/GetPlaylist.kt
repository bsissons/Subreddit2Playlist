package com.example.subreddit2playlist

//import android.net.wifi.hotspot2.pps.Credential
//import com.google.api.client.http.HttpTransport
//import com.google.api.client.googleapis.json.GoogleJsonResponseException
//import com.google.api.client.json.JsonFactory
//import com.google.api.client.json.gson
//import com.google.api.client.json.jackson2.JacksonFactory
//import java.io.File

import android.R
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.Playlist
import com.google.api.services.youtube.model.PlaylistSnippet
import com.google.api.services.youtube.model.PlaylistStatus
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.security.GeneralSecurityException
import java.util.*
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential


//import java.util.*

class GetPlaylist {
    private val clientSecretsFile = "/client_secret.json"
    private val scopes: Collection<String> =
        listOf("https://www.googleapis.com/auth/youtube.force-ssl")
    private val applicationName = "subreddit2playlist"
    private val myJSonFactory: GsonFactory = GsonFactory.getDefaultInstance()

    /**
     * Create an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    //@Throws(IOException::class)
    private fun authorize(httpTransport: NetHttpTransport?): Credential? {
        println("DDDD start authorize")
        // Load client secrets
        /*
        val inputStream: InputStream? = GetPlaylist::class.java.getResourceAsStream(clientSecretsFile)
        if (inputStream == null) {
            println("DDDD failed to get inputstream")
            return null
        }

        println("DDDD got the resource stream")
        val clientSecrets = GoogleClientSecrets.load(myJSonFactory, InputStreamReader(inputStream))

        println("DDDD got the client secret")
        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
            httpTransport,
            myJSonFactory,
            clientSecrets,
            scopes
        ).build()
        println("DDDD end authorize")
        val receiver = LocalServerReceiver.Builder().setPort(-1).build()
        println("DDDD receiver is ${receiver.toString()}")
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("benjamin.sissons@gmail.com")

         */

        //val credential = GoogleCredential().setAccessToken()
        val credential: AppIdentityCredential = AppIdentityCredential(scopes)

/*
        val mCredential = GoogleAccountCredential.usingOAuth2(
            getApplicationContext(),
            scopes
        ).setBackOff(ExponentialBackOff())
        //val httpTransport: HttpTransport = AndroidHttp.newCompatibleTransport()
        val mService = YouTube.Builder(
            httpTransport, myJSonFactory, mCredential
        ).setApplicationName(applicationName).build()
 */
    }

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    /*
    @get:Throws(
        GeneralSecurityException::class,
        IOException::class
    )
    */
    private fun getService() : YouTube? {
        println("DDDD getService")
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val credential : Credential = authorize(httpTransport) ?: return null
        return YouTube.Builder(httpTransport, myJSonFactory, credential)
            .setApplicationName(applicationName)
            .build()
    }

    fun printLog() {
        println("DDDD hello world")
    }



    /**
     * Call function to create API service object. Define and
     * execute API request. Print API response.
     *
     * @throws GeneralSecurityException, IOException, GoogleJsonResponseException
     */
    /*
    @Throws(
        GeneralSecurityException::class,
        IOException::class,
        GoogleJsonResponseException::class
    )
    @JvmStatic
     */
    fun returnPlaylist() : Playlist? {
        println("DDDD before getService")
        val youtubeService: YouTube = getService() ?: return null
        println("DDDD after getService")

        // Define the Playlist object, which will be uploaded as the request body.
        val playlist = Playlist()

        // Add the snippet object property to the Playlist object.
        val snippet = PlaylistSnippet()
        snippet.defaultLanguage = "en"
        snippet.description = "This is a sample playlist description."
        val tags = arrayOf(
            "sample playlist",
            "API call"
        )
        snippet.tags = listOf(*tags)
        snippet.title = "Sample playlist created via API"
        playlist.snippet = snippet

        // Add the status object property to the Playlist object.
        val status = PlaylistStatus()
        status.privacyStatus = "private"
        playlist.status = status

        // Define and execute the API request
        val part = mutableListOf("snippet", "status")
        println("DDDD before playlist request")
        val request: YouTube.Playlists.Insert? = youtubeService.playlists()
            ?.insert(part, playlist)
        val response: Playlist = request?.execute() ?: return null
        println("DDDD insert response")
        println(response)

        // Return the playlist that was inserted
        val listOfPlaylistRequest: YouTube.Playlists.List = youtubeService.playlists().list(part)
        println("DDDD before playlist response")
        val playlistResponse = listOfPlaylistRequest.execute()
        println("DDDD playlist response")
        println(playlistResponse)
        //return playlistResponse
        return response
    }
}