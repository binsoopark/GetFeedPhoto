package com.soobinpark.getfeedphoto.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.soobinpark.getfeedphoto.R
import com.soobinpark.getfeedphoto.adapter.FeedRecyclerAdapter
import com.soobinpark.getfeedphoto.data.FeedItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<FeedItem>()
        list.add(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, "테스트 피드 제목1"))
        list.add(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, "테스트 피드 제목2"))
        list.add(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, "테스트 피드 제목3"))
        list.add(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, "테스트 피드 제목4"))
        list.add(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, "테스트 피드 제목5"))

        val adapter = FeedRecyclerAdapter(list)
        recyclerview_main_feed.adapter = adapter

        recyclerview_main_feed.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
