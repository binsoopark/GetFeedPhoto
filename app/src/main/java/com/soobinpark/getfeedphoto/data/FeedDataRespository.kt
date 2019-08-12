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
    val feedDataMap = HashMap<String, TimelineFeedData>()

    override fun getFeedDataByFeedId(feedId: String, callback: IFeedDataControl.Callback?) {
        feedDataMap[feedId]?.let {
            val arrayList = ArrayList<TimelineFeedData>()
            arrayList.add(it)
            callback?.onCompleted(arrayList)
            return
        }.let {
            callback?.onError("Could not get matching data from saved list.")
        }
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
                    storeFeedDataToMap(response.body()!!) // Search용 관리를 위해 map으로 변환한다.
                    callback?.onCompleted(feedDataList)
                }
            }

            override fun onFailure(call: Call<List<TimelineFeedData>>?, t: Throwable?) {
                Log.d(TAG, "onFailure: "+t.toString())
                callback?.onError(t.toString())
            }
        })
    }

    private fun storeFeedDataToMap(list: List<TimelineFeedData>) {
        for(item in list) {
            feedDataMap[item.id_str] = item
        }
    }

}