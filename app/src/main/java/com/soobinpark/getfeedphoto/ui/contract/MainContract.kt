package com.soobinpark.getfeedphoto.ui.contract

import com.soobinpark.getfeedphoto.adapter.contract.FeedRecyclerAdapterContract
import com.soobinpark.getfeedphoto.data.FeedDataRespository

interface MainContract {
    interface View {
        fun notifyUsingToast(text: String)
        fun moveToFeedDetailScreen(strId: String)
    }

    interface Presenter {
        var view: View
        var feedDataRepo: FeedDataRespository

        var adapterModel: FeedRecyclerAdapterContract.Model
        var adapterView: FeedRecyclerAdapterContract.View?

        fun loadItems()

        fun loadMoreFeed()
    }

}