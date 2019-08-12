package com.soobinpark.getfeedphoto.ui

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.soobinpark.getfeedphoto.R
import com.soobinpark.getfeedphoto.common.Constants
import com.soobinpark.getfeedphoto.ui.contract.FeedDetailContract
import com.soobinpark.getfeedphoto.ui.presenter.FeedDetailPresenter
import kotlinx.android.synthetic.main.activity_feed_detail.*
import kotlinx.android.synthetic.main.my_toolbar.*

class FeedDetailActivity: AppCompatActivity(), FeedDetailContract.View {
    companion object {
        private const val TAG = "FeedDetailActivity"
    }

    private val presenter: FeedDetailPresenter = FeedDetailPresenter().apply {
        view = this@FeedDetailActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_detail)

        my_toolbar.title = "Get Feed Photo"
        setSupportActionBar(my_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Log.d(TAG, "onCreate")
        intent?.let {
            val strId = it.getStringExtra(Constants.EXTRA_KEY_FEED_ID_STR)
            Log.d(TAG, "onCreate. Intent: $strId")
            if(strId != null){
                Log.d(TAG, "onCreate $strId")
                presenter.loadItemById(strId)
            }
        }

        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun notifyUsingToast(text: String) {
        Toast.makeText(this, "FeedDetailActivity: $text", Toast.LENGTH_SHORT).show()
    }

    override fun fillContents(
        authorName: String,
        authorImageUrl: String,
        datetime: String,
        mainImageUrl: String?,
        text: String
    ) {
        Log.d(TAG, "fillContents")
        tv_feed_detail_content_author.text = authorName
        tv_feed_detail_content_datetime.text = datetime
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_feed_detail_content_text_mention.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            tv_feed_detail_content_text_mention.text = text
        }

        Glide.with(this).load(authorImageUrl).into(iv_feed_detail_content_author)
        mainImageUrl?.let {
            Glide.with(this).load(it).into(iv_feed_detail_content_media_picture)
            iv_feed_detail_content_media_picture.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(0, 0)
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(0, 0)
    }
}