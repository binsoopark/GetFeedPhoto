package com.soobinpark.getfeedphoto.ui.presenter

import android.content.Context
import android.util.Log
import com.soobinpark.getfeedphoto.adapter.contract.FeedRecyclerAdapterContract
import com.soobinpark.getfeedphoto.data.FeedDataRespository
import com.soobinpark.getfeedphoto.data.FeedItem
import com.soobinpark.getfeedphoto.data.IFeedDataControl
import com.soobinpark.getfeedphoto.data.TimelineFeedData

class MainPresenter: MainContract.Presenter {
    lateinit override var view: MainContract.View
    lateinit override var feedDataRepo: FeedDataRespository
    lateinit override var adapterModel: FeedRecyclerAdapterContract.Model
    override var adapterView: FeedRecyclerAdapterContract.View? = null
        set(value) {
            field = value
            field?.onClick = {onClickListener(it)}
        }

    private fun onClickListener(pos: Int) {
        adapterModel.getItem(pos).let {
            view.notifyUsingToast("Information: ${it.id_str}, ${it.title}")
        }
    }

    override fun loadItems(context: Context) {
        feedDataRepo.getRecentlyFeedData(object: IFeedDataControl.Callback {
            override fun onCompleted(list: ArrayList<TimelineFeedData>) {
                Log.d("TEST", "onCompleted")
                // list에 아이템을 모두 추가해준다
                val array = ArrayList<FeedItem>()
                for (item in list) {
                    item?.let {
                        val txt = it.text
                        if(it.entities.media != null) {
                            val imgUrl = it.entities.media[0].media_url
                            array.add(FeedItem(it.id_str, imgUrl, txt))
                        } else {
                            array.add(FeedItem(it.id_str, null, txt))
                        }

                        adapterModel.addItems(array)
                        // adapter에 아이템이 새롭게 반영된 것을 알려준다.
                        adapterView?.notifyAdapter()
                    }
                }

            }

            override fun onError(errorMsg: String) {
                view.notifyUsingToast(errorMsg)
            }
        })
    }
}