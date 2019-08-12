package com.soobinpark.getfeedphoto.adapter.contract

import android.view.View
import com.soobinpark.getfeedphoto.data.model.FeedItem

interface FeedRecyclerAdapterContract {
    interface View {
//        var onClick: ((Int) -> android.view.View.OnClickListener)
        var onClick: ((Int) -> Unit)?

        fun notifyAdapter()
    }

    interface Model {
        fun addItems(feedDataItems: ArrayList<FeedItem>)

        fun clearItem()

        fun getItem(pos: Int): FeedItem
    }
}