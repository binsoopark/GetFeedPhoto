package com.soobinpark.getfeedphoto.data

import android.util.Log
import com.soobinpark.getfeedphoto.data.model.TimelineFeedData
import com.soobinpark.getfeedphoto.data.remote.IRetrofitTwitter
import com.soobinpark.getfeedphoto.data.remote.Okhttp3Retrofit2Manager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

object FeedDataRespository: IFeedDataControl {
    val TAG = "FeedDataRepository"

    val feedDataList = ArrayList<TimelineFeedData>()

    override fun getFeedDataByFeedId(feedId: String, callback: IFeedDataControl.Callback?) {
        for(item in feedDataList) {
            if(item.id_str == feedId) {
                val arrayList = ArrayList<TimelineFeedData>()
                arrayList.add(item)
                callback?.onCompleted(arrayList)
                return
            }
        }
        callback?.onError("Could not get matching data from saved list.")
    }

    override fun getRecentlyFeedData(callback: IFeedDataControl.Callback?) {
        val restClient: IRetrofitTwitter = Okhttp3Retrofit2Manager.getRetrofitService(IRetrofitTwitter::class.java)

        val currentFeed = restClient.requestStatusHomeTimeline("true")
        currentFeed.enqueue(object : Callback<List<TimelineFeedData>> {
            override fun onResponse(call: Call<List<TimelineFeedData>>?, response: Response<List<TimelineFeedData>>?) {
                Log.d(TAG, "onResponse")
                if(response != null && response.isSuccessful) {
                    feedDataList.clear()
                    feedDataList.addAll(response.body()!!)
                    callback?.onCompleted(feedDataList)
                }
            }

            override fun onFailure(call: Call<List<TimelineFeedData>>?, t: Throwable?) {
                Log.d(TAG, "onFailure: "+t.toString())
                callback?.onError(t.toString())
            }
        })
    }

}