package com.soobinpark.getfeedphoto.data

interface IFeedDataControl {
    interface Callback {
        fun onCompleted(list: ArrayList<TimelineFeedData>)
        fun onError(errorMsg: String)
    }

    fun getFeedDataByFeedId(feedId: String, callback: Callback?)

    fun getRecentlyFeedData(callback: Callback?)
}