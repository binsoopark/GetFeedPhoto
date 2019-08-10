package com.soobinpark.getfeedphoto.ui

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.soobinpark.getfeedphoto.R
import com.soobinpark.getfeedphoto.adapter.FeedRecyclerAdapter
import com.soobinpark.getfeedphoto.data.FeedItem
import com.soobinpark.getfeedphoto.data.TimelineFeedData
import com.soobinpark.getfeedphoto.data.remote.IRetrofitTwitter
import com.soobinpark.getfeedphoto.data.remote.Okhttp3Retrofit2Manager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val adapter: FeedRecyclerAdapter

    init {
        val list = ArrayList<FeedItem>()
        adapter = FeedRecyclerAdapter(list)
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview_main_feed.adapter = adapter

        recyclerview_main_feed.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun onResume() {
        super.onResume()

        val restClient: IRetrofitTwitter = Okhttp3Retrofit2Manager.getRetrofitService(IRetrofitTwitter::class.java)

        val currentFeed = restClient.requestStatusHomeTimeline("true")
        currentFeed.enqueue(object : Callback<List<TimelineFeedData>> {
            override fun onResponse(call: Call<List<TimelineFeedData>>?, response: Response<List<TimelineFeedData>>?) {
                Log.d(TAG, "onResponse")
                if(response != null && response.isSuccessful)
                    refreshCurrentNewFeedsUI(response.body())
            }

            override fun onFailure(call: Call<List<TimelineFeedData>>?, t: Throwable?) {
                Log.d(TAG, "onFailure: "+t.toString())
                errorMessage(message = t.toString())
            }
        })
    }

    private fun refreshCurrentNewFeedsUI(data: List<TimelineFeedData?>?) {
        Log.d(TAG, "data: "+data.toString())
        data?.let {
//            Log.d(TAG, data.feed.feeds)
            adapter.clearIteams()
//            adapter.addItem(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, "테스트 피드 제목1"))
//            adapter.addItem(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, "테스트 피드 제목2"))
//            adapter.addItem(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, "테스트 피드 제목3"))
//            adapter.addItem(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, "테스트 피드 제목4"))
//            adapter.addItem(FeedItem(getDrawable(R.mipmap.ic_launcher)!!, "테스트 피드 제목5"))
            for (item in data) {
                item?.let {
                    val txt = it.text
                    if(it.entities.media != null) {
                        val imgUrl = it.entities.media[0].media_url
                        adapter.addItem(FeedItem(imgUrl, txt))
                    } else {
                        adapter.addItem(FeedItem(null, txt))
                    }
                }
            }
        }

    }

    private fun errorMessage(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
