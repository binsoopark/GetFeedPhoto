package com.soobinpark.getfeedphoto.data.remote

import com.soobinpark.getfeedphoto.data.TimelineFeedData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofitTwitter {
    @GET("/statuses/home_timeline.json")
    fun requestStatusHomeTimeline(
        @Query("exclude_replies")   exclude_replies:String
    ) : Call<TimelineFeedData>
}