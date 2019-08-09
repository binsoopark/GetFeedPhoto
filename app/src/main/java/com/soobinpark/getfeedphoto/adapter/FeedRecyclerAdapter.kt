package com.soobinpark.getfeedphoto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.soobinpark.getfeedphoto.R
import com.soobinpark.getfeedphoto.data.FeedItem
import kotlinx.android.synthetic.main.feed_list_item.view.*

class FeedRecyclerAdapter(private val items: ArrayList<FeedItem>) :
    RecyclerView.Adapter<FeedRecyclerAdapter.ViewHolder>() {

    override fun getItemCount() = items.size

    // View가 생성되면 호출됨
    override fun onBindViewHolder(holder: FeedRecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it -> Toast.makeText(it.context, "Clicked: ${item.title}", Toast.LENGTH_SHORT).show() }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    // RecyclerView가 초기화 될 때 호출됨
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedRecyclerAdapter.ViewHolder {
        val inflateView = LayoutInflater.from(parent.context).inflate(R.layout.feed_list_item, parent, false)
        return FeedRecyclerAdapter.ViewHolder(inflateView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: FeedItem) {
            view.iv_main_list_item_thumbnail.setImageDrawable(item.image)
            view.tv_main_list_item_title.text = item.title
            view.setOnClickListener(listener)
        }
    }
}