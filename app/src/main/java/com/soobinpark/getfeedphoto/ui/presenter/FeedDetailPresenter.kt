package com.soobinpark.getfeedphoto.ui.presenter

import com.soobinpark.getfeedphoto.data.FeedDataRespository
import com.soobinpark.getfeedphoto.data.IFeedDataControl
import com.soobinpark.getfeedphoto.data.model.TimelineFeedData
import com.soobinpark.getfeedphoto.ui.contract.FeedDetailContract

class FeedDetailPresenter : FeedDetailContract.Presenter {
    override lateinit var view: FeedDetailContract.View
    override var feedDataRepo = FeedDataRespository

    override fun loadItemById(strId: String) {
        feedDataRepo.getFeedDataByFeedId(strId, object: IFeedDataControl.Callback {
            override fun onCompleted(list: ArrayList<TimelineFeedData>) {
                list[0].let {

                    val media = if(it.entities.media != null) { if(it.entities.media.size != 0) it.entities.media[0] else null} else null
                    view.fillContents(it.user.screen_name,
                        it.user.profile_image_url_https,
                        it.created_at,
                        media?.media_url?:"",
                        it.text
                    )
                }
            }

            override fun onError(errorMsg: String) {
                view.notifyUsingToast(errorMsg)
            }

        })
    }

}