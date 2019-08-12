package com.soobinpark.getfeedphoto.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.soobinpark.getfeedphoto.R
import com.soobinpark.getfeedphoto.adapter.FeedRecyclerAdapter
import com.soobinpark.getfeedphoto.common.Constants
import com.soobinpark.getfeedphoto.data.FeedDataRespository
import com.soobinpark.getfeedphoto.data.model.FeedItem
import com.soobinpark.getfeedphoto.ui.presenter.MainContract
import com.soobinpark.getfeedphoto.ui.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.my_toolbar.*

class MainActivity : AppCompatActivity(), MainContract.View {
    private val adapter: FeedRecyclerAdapter
    private val presenter: MainPresenter

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

        my_toolbar.title = "Get Feed Photo"
        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        recyclerview_main_feed.adapter = adapter

        recyclerview_main_feed.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        recyclerview_main_feed.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerview_main_feed.canScrollVertically(-1)) {
                    Log.d(TAG, "top of the list")
                    btn_more.visibility = View.GONE
                } else if (!recyclerview_main_feed.canScrollVertically(1)) {
                    Log.d(TAG, "end of the list")
                    btn_more.visibility = View.VISIBLE
                } else {
                    Log.d(TAG, "idle")
                    btn_more.visibility = View.GONE
                }
            }
        })

        presenter.loadItems(this)

        btn_more.setOnClickListener {
            presenter.loadMoreFeed(this)
            it.visibility = View.GONE
        }
    }

    override fun notifyUsingToast(text: String) {
        Toast.makeText(this, "MainActivity $text", Toast.LENGTH_SHORT).show()
    }

    override fun moveToFeedDetailScreen(strId: String) {
        Log.d(TAG, "moveToFeedDetailScreen $strId")
        val intent = Intent(this@MainActivity, FeedDetailActivity::class.java)
        intent.putExtra(Constants.EXTRA_KEY_FEED_ID_STR, strId)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}
