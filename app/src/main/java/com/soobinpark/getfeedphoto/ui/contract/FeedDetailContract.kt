package com.soobinpark.getfeedphoto.ui.contract

import com.soobinpark.getfeedphoto.data.FeedDataRespository

interface FeedDetailContract {
    interface View {
        fun notifyUsingToast(text: String)

        fun fillContents(authorName: String,
                         authorImageUrl: String,
                         datetime: String,
                         mainImageUrl: String?,
                         text: String)
    }

    interface Presenter {
        var view: View
        var feedDataRepo: FeedDataRespository

        fun loadItemById(strId: String)
    }
}