package com.soobinpark.getfeedphoto.data.remote

import com.soobinpark.getfeedphoto.data.model.TimelineFeedData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofitTwitter {
    @GET("/1.1/statuses/home_timeline.json")
    fun requestStatusHomeTimeline(
        @Query("exclude_replies")   exclude_replies:String,
        @Query("count") count: Int,
        @Query("max_id") max_id: Long
    ) : Call<List<TimelineFeedData>>
}