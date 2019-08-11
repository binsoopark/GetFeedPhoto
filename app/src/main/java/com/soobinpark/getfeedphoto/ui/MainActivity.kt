package com.soobinpark.getfeedphoto.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.soobinpark.getfeedphoto.R
import com.soobinpark.getfeedphoto.adapter.FeedRecyclerAdapter
import com.soobinpark.getfeedphoto.data.FeedDataRespository
import com.soobinpark.getfeedphoto.data.FeedItem
import com.soobinpark.getfeedphoto.ui.presenter.MainContract
import com.soobinpark.getfeedphoto.ui.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {
    val adapter: FeedRecyclerAdapter
    val presenter: MainPresenter

    init {
        val list = ArrayList<FeedItem>()
        adapter = FeedRecyclerAdapter(list)
        presenter = MainPresenter().apply {
            view = this@MainActivity
            feedDataRepo = FeedDataRespository
            adapterModel = adapter
            adapterView = adapter
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview_main_feed.adapter = adapter

        recyclerview_main_feed.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        recyclerview_main_feed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerview_main_feed.canScrollVertically(-1)) {
                    Log.d(TAG, "top of the list")
                } else if (!recyclerview_main_feed.canScrollVertically(1)) {
                    Log.d(TAG, "end of the list")
                } else {
                    Log.d(TAG, "idle")
                }
            }
        })

        presenter.loadItems(this)
    }

    override fun notifyUsingToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
