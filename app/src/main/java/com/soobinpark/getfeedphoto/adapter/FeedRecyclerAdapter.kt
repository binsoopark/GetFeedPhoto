package com.soobinpark.getfeedphoto.adapter

import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soobinpark.getfeedphoto.R
import com.soobinpark.getfeedphoto.adapter.contract.FeedRecyclerAdapterContract
import com.soobinpark.getfeedphoto.data.model.FeedItem
import kotlinx.android.synthetic.main.feed_list_item.view.*

class FeedRecyclerAdapter(private val items: ArrayList<FeedItem>) :
    RecyclerView.Adapter<FeedRecyclerAdapter.ViewHolder>(), FeedRecyclerAdapterContract.View, FeedRecyclerAdapterContract.Model {
    override var onClick: ((Int) -> Unit)? = null

    override fun addItems(feedDataItems: ArrayList<FeedItem>) {
        items.addAll(feedDataItems)
    }

    override fun clearItem() {
        items.clear()
    }

    override fun getItem(pos: Int): FeedItem = items[pos]

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    companion object {
        const val TAG = "FeedRecyclerAdapter"
    }

    override fun getItemCount() = items.size

    // View가 생성되면 호출됨
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val listener = onClick
        holder.apply {
            bind(listener, item, position)
            itemView.tag = item
        }
    }

    // RecyclerView가 초기화 될 때 호출됨
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflateView = LayoutInflater.from(parent.context).inflate(R.layout.feed_list_item, parent, false)
        return ViewHolder(inflateView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: ((Int) -> Unit)?, item: FeedItem, pos: Int) {
            Log.d(TAG, "item.imageUrl: ${item.imageUrl}")
            item.imageUrl.let { Glide.with(view).load(it).into(view.iv_main_list_item_thumbnail) }
            view.setOnClickListener{ listener?.invoke(pos) }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.tv_main_list_item_title.text = Html.fromHtml(item.title, Html.FROM_HTML_MODE_COMPACT)
            } else {
                view.tv_main_list_item_title.text = item.title
            }
        }
    }
}