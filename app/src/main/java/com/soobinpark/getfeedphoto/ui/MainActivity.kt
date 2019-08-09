package com.soobinpark.getfeedphoto.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soobinpark.getfeedphoto.R
import com.soobinpark.getfeedphoto.adapter.FeedRecyclerAdapter
import com.soobinpark.getfeedphoto.data.FeedItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<FeedItem>()
        list.add(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, getString(R.string.search_menu_title)))
        list.add(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, getString(R.string.search_menu_title)))
        list.add(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, getString(R.string.search_menu_title)))
        list.add(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, getString(R.string.search_menu_title)))
        list.add(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, getString(R.string.search_menu_title)))

        val adapter = FeedRecyclerAdapter(list)

    }
}
