package com.congressdatacontest.congressdatacontest_android.feature.billdetail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.congressdatacontest.congressdatacontest_android.databinding.ItemNewsBinding
import kotlin.math.roundToInt

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
            binding.tvPositiveCount.text = newsData.positive.roundToInt().toString()
            binding.tvNeutralCount.text = newsData.neutral.roundToInt().toString()
            binding.tvNegativeCount.text = newsData.negative.roundToInt().toString()

            Log.i("bind", "newsData.positive: ${newsData.positive}")
            Log.i("bind", "newsData.neutral: ${newsData.neutral}")
            Log.i("bind", "newsData.negative: ${newsData.negative}")

            Log.i("bind", "newsData.positive: ${"%.6f".format(newsData.positive)}")
            Log.i("bind", "newsData.neutral: ${"%.6f".format(newsData.neutral)}")
            Log.i("bind", "newsData.negative: ${"%.6f".format(newsData.negative)}")

            Glide.with(itemView).load(newsData.image).into(binding.ivNews)
        }
    }

    companion object NewsDiffUtil : DiffUtil.ItemCallback<NewsData>() {
        override fun areItemsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(oldItem: NewsData, newItem: NewsData): Boolean {
            return oldItem == newItem
        }
    }
}