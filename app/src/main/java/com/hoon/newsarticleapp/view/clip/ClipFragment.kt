package com.hoon.newsarticleapp.view.clip

import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoon.newsarticleapp.BaseFragment
import com.hoon.newsarticleapp.R
import com.hoon.newsarticleapp.data.model.ArticleModel
import com.hoon.newsarticleapp.databinding.FragmentClipBinding
import com.hoon.newsarticleapp.extensions.toast
import com.hoon.newsarticleapp.view.adapter.ArticleAdapter
import com.hoon.newsarticleapp.view.home.HomeFragment
import com.hoon.newsarticleapp.view.web_view.WebViewActivity
import org.koin.android.viewmodel.ext.android.viewModel

class ClipFragment : BaseFragment<ClipViewModel, FragmentClipBinding>() {
    override val viewModel by viewModel<ClipViewModel>()

    override fun getViewBinding() = FragmentClipBinding.inflate(layoutInflater)

    private val adapter by lazy { ArticleAdapter(adapterClickHandler, adapterMenuItemClickHandler) }

    override fun observeData() {
        viewModel.clipViewModelLiveData.observe(this) {
            when (it) {
                is ClipState.Uninitialized -> {
                    viewModel.observeArticleFlow()
                }
                is ClipState.Success.FetchData -> {
                    handleFetchData(it.models)
                }
                else -> {}
            }
        }
    }

    private fun handleFetchData(models: List<ArticleModel>) {
        adapter.submitList(models)
    }

    override fun initViews(): Unit = with(binding) {
        rvArticle.adapter = adapter
        rvArticle.layoutManager =
            LinearLayoutManager(this@ClipFragment.context, RecyclerView.VERTICAL, false)

        searchArticle()
    }

    private fun searchArticle() = with(binding) {
        var startTime = System.currentTimeMillis()
        var endTime: Long

        binding.etSearch.text =
            Editable.Factory.getInstance().newEditable(viewModel.searchTextWatcher)

        etSearch.addTextChangedListener { text: Editable? ->
            endTime = System.currentTimeMillis()
            if (endTime - startTime > HomeFragment.SEARCH_ARTICLES_TIME_DELAY) {
                text?.let {
                    val query = it.toString().trim()
                    viewModel.searchTextWatcher = query
                    viewModel.observeArticleFlow()
                }
            }
            startTime = endTime
        }
    }

    private val adapterClickHandler: (model: ArticleModel) -> Unit = {
        val intent = WebViewActivity.newIntent(requireContext(), it.webUrl)
        startActivity(intent)
    }

    private val adapterMenuItemClickHandler: (ArticleModel) -> Unit = { model ->
        viewModel.deleteArticleInDB(model)
        context?.let { it.toast(resources.getString(R.string.toast_delete_article)) }
    }
}