package com.hoon.newsarticleapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hoon.newsarticleapp.R
import com.hoon.newsarticleapp.data.model.ArticleModel
import com.hoon.newsarticleapp.databinding.ItemArticleBinding

class ArticleAdapter(
    private val onItemClickListener: (ArticleModel) -> Unit,
    private val onMenuItemClickListener: (ArticleModel) -> Unit
) : ListAdapter<ArticleModel, ArticleAdapter.ViewHolder>(diffutils) {
    inner class ViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ArticleModel) {
            binding.article = model

            binding.root.setOnClickListener {
                onItemClickListener(model)
            }

            binding.tvOptions.setOnClickListener {
                val menu = PopupMenu(binding.root.context, it)
                menu.inflate(R.menu.menu_rv_item_delete)
                menu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.delete -> {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffutils = object : DiffUtil.ItemCallback<ArticleModel>() {
            override fun areItemsTheSame(
                oldItem: ArticleModel,
                newItem: ArticleModel
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: ArticleModel,
                newItem: ArticleModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}