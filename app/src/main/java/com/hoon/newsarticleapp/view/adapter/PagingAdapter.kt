package com.hoon.newsarticleapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hoon.newsarticleapp.R
import com.hoon.newsarticleapp.data.model.ArticleModel
import com.hoon.newsarticleapp.databinding.ItemArticleBinding

class PagingAdapter(
    private val onItemClickListener: (ArticleModel) -> Unit,
    private val onMenuItemClickListener: (ArticleModel) -> Unit
) : PagingDataAdapter<ArticleModel, PagingAdapter.ViewHolder>(diffUtil) {

    override fun onBindViewHolder(holder: PagingAdapter.ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.onBind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagingAdapter.ViewHolder {
        return ViewHolder(
            binding = ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class ViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(model: ArticleModel) {
            binding.article = model

            binding.root.setOnClickListener {
                onItemClickListener(model)
            }

            binding.tvOptions.setOnClickListener {
                val menu = PopupMenu(binding.root.context, it)
                menu.inflate(R.menu.menu_rv_item_clip)
                menu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.clip -> {
                            onMenuItemClickListener(model)
                            return@setOnMenuItemClickListener true
                        }
                        else -> { return@setOnMenuItemClickListener false }
                    }
                }
                menu.show()
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ArticleModel>() {
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}