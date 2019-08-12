package com.soobinpark.getfeedphoto.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.soobinpark.getfeedphoto.R
import kotlinx.android.synthetic.main.activity_login.*
import org.apache.http.util.EncodingUtils

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val url = "https://api.twitter.com/oauth/request_token"
        val postData = "oauth_callback=https://soobinpark.com/twitter_callback"
        val encodedData = EncodingUtils.getBytes(postData, "BASE64")
        webview_login?.postUrl(url, encodedData)
    }
}