package com.congressdatacontest.congressdatacontest_android.feature.billdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.congressdatacontest.congressdatacontest_android.databinding.ItemNewsBinding

class NewsAdapter(
    private val onClick: (NewsData) -> Unit
): ListAdapter<NewsData, NewsAdapter.NewsViewHolder>(NewsDiffUtil) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val binding =
            ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val billEvaluationProcess = getItem(position)
        holder.bind(billEvaluationProcess)
    }

    class NewsViewHolder(
        private val binding: ItemNewsBinding,
        private val onClick: (NewsData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(newsData: NewsData) {
            binding.root.setOnClickListener {
                onClick(newsData)
            }

            binding.tvTitleNews.text = newsData.title
            binding.tvPositiveCount.text = newsData.positive.toString()
            binding.tvNeutralCount.text = newsData.neutral.toString()
            binding.tvNegativeCount.text = newsData.negative.toString()

            Glide.with(itemView).load(newsData.image).into(binding.ivNews)
        }
    }

    companion object NewsDiffUtil : DiffUtil.ItemCallback<NewsData>() {
        override fun areItemsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
            return oldItem == newItem
        }
    }
}