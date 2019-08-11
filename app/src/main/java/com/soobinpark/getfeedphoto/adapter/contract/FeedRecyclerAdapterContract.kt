package com.soobinpark.getfeedphoto.adapter.contract

import com.soobinpark.getfeedphoto.data.FeedItem

interface FeedRecyclerAdapterContract {
    interface View {
        var onClick: ((Int) -> Unit?)?

        fun notifyAdapter()
    }

    interface Model {
        fun addItems(feedDataItems: ArrayList<FeedItem>)

        fun clearItem()

        fun getItem(pos: Int): FeedItem
    }
}