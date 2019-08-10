package com.soobinpark.getfeedphoto.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
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
    companion object {
        private const val TAG = "MainActivity"
    }

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

    override fun onResume() {
        super.onResume()

        val restClient: IRetrofitTwitter = Okhttp3Retrofit2Manager.getRetrofitService(IRetrofitTwitter::class.java)

        val currentFeed = restClient.requestStatusHomeTimeline("true")
        currentFeed.enqueue(object : Callback<List<TimelineFeedData>> {
            override fun onResponse(call: Call<List<TimelineFeedData>>?, response: Response<List<TimelineFeedData>>?) {
                Log.d(TAG, "onResponse")
                if(response != null && response.isSuccessful)
                    refreshCurrentNewFeedsUI(response.body()!![0])
            }

            override fun onFailure(call: Call<List<TimelineFeedData>>?, t: Throwable?) {
                Log.d(TAG, "onFailure: "+t.toString())
                errorMessage(message = t.toString())
            }
        })
    }

    private fun refreshCurrentNewFeedsUI(data: TimelineFeedData?) {
        Log.d(TAG, "data: "+data.toString())
        data?.let {
//            Log.d(TAG, data.feed.feeds)
        }

    }

    private fun errorMessage(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
