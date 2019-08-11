package com.soobinpark.getfeedphoto.ui.presenter

import android.content.Context
import com.soobinpark.getfeedphoto.adapter.contract.FeedRecyclerAdapterContract
import com.soobinpark.getfeedphoto.data.FeedDataRespository

interface MainContract {
    interface View {
        fun notifyUsingToast(text: String)
    }

    interface Presenter {
        var view: MainContract.View
        var feedDataRepo: FeedDataRespository

        var adapterModel: FeedRecyclerAdapterContract.Model
        var adapterView: FeedRecyclerAdapterContract.View?

        fun loadItems(context: Context)
    }

}