package com.chistoedet.android.istustudents.ui.main.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chistoedet.android.istustudents.BR
import com.chistoedet.android.istustudents.R
import com.chistoedet.android.istustudents.databinding.NewsItemBinding
import com.vk.sdk.api.wall.dto.WallWallpostFull

private val TAG = NewsListAdapter::class.simpleName
class NewsListAdapter() : PagingDataAdapter<WallWallpostFull, NewsListAdapter.NewsHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<NewsItemBinding>(inflater, viewType, parent, false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) = holder.bindMessage(getItem(position))

    inner class NewsHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bindMessage(news: WallWallpostFull?) {
            binding.setVariable(BR.posts, news)
        }

        override fun onClick(v: View?) {

        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<WallWallpostFull> =
            object : DiffUtil.ItemCallback<WallWallpostFull>() {
                override fun areItemsTheSame(oldItem: WallWallpostFull, newItem: WallWallpostFull): Boolean {
                    return oldItem === newItem
                }

                override fun areContentsTheSame(oldItem: WallWallpostFull, newItem: WallWallpostFull): Boolean {
                    return oldItem.equals(newItem)
                }
            }
    }


    override fun getItemViewType(position: Int): Int = R.layout.news_item

}