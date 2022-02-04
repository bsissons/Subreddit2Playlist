package com.example.subreddit2playlist

//import android.net.wifi.hotspot2.pps.Credential
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.Playlist
import com.google.api.services.youtube.model.PlaylistSnippet
import com.google.api.services.youtube.model.PlaylistStatus
import java.io.IOException
import java.io.InputStreamReader
import java.security.GeneralSecurityException
import java.util.*

class GetPlaylist {
    private val clientSecrets = "client_secret.json"
    private val scopes: Collection<String> =
        listOf("https://www.googleapis.com/auth/youtube.force-ssl")
    private val applicationName = "subreddit2playlist"
    private val myJSonFactory: JsonFactory = JacksonFactory.getDefaultInstance()

    /**
     * Create an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    //@Throws(IOException::class)
    private fun authorize(httpTransport: NetHttpTransport?): Credential {
        // Load client secrets.
        val inPlaylist = GetPlaylist::class.java.getResourceAsStream(clientSecrets)
        val clientSecrets = GoogleClientSecrets.load(myJSonFactory, InputStreamReader(inPlaylist))
        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
            httpTransport,
            myJSonFactory,
            clientSecrets,
            scopes
        ).build()
        return AuthorizationCodeInstalledApp(flow, LocalServerReceiver()).authorize("user")
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
    private fun getService() : YouTube {
        val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
        val credential = authorize(httpTransport)
        return YouTube.Builder(httpTransport, myJSonFactory, credential)
            .setApplicationName(applicationName)
            .build()
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
    fun returnPlaylist() : Playlist {
        val youtubeService: YouTube = getService()

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
        val request: YouTube.Playlists.Insert = youtubeService.playlists()
            .insert(mutableListOf("snippet", "status"), playlist)
        val response: Playlist = request.execute()
        println(response)
        return response
    }
}