package com.soobinpark.getfeedphoto.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.soobinpark.getfeedphoto.R

class FeedDetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_feed_detail)

        intent?.let {
            it.getStringExtra("id")
        }
    }
}