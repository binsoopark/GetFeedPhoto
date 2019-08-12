package com.soobinpark.getfeedphoto.data

import com.soobinpark.getfeedphoto.data.model.TimelineFeedData

interface IFeedDataControl {
    interface Callback {
        fun onCompleted(list: ArrayList<TimelineFeedData>)
        fun onError(errorMsg: String)
    }

    fun getFeedDataByFeedId(feedId: String, callback: Callback?)

    fun getRecentlyFeedData(callback: Callback?)

    fun getBottomFeedId(): Long

    fun getFeedListFromFeedId(maxId: Long, callback: Callback?)
}