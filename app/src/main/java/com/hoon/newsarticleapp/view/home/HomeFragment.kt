package com.hoon.newsarticleapp.view.home

import android.text.Editable
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hoon.newsarticleapp.BaseFragment
import com.hoon.newsarticleapp.R
import com.hoon.newsarticleapp.data.model.ArticleModel
import com.hoon.newsarticleapp.databinding.FragmentHomeBinding
import com.hoon.newsarticleapp.extensions.toast
import com.hoon.newsarticleapp.view.adapter.PagingAdapter
import com.hoon.newsarticleapp.view.adapter.PagingLoadStateAdapter
import com.hoon.newsarticleapp.view.web_view.WebViewActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    companion object {
        const val SEARCH_ARTICLES_TIME_DELAY = 50L
    }

    private val pagingAdapter by lazy { PagingAdapter(adapterClickHandler, adapterMenuItemClickHandler) }

    override fun initViews(): Unit = with(binding) {
        rvArticle.adapter = pagingAdapter.withLoadStateFooter(PagingLoadStateAdapter())
        rvArticle.layoutManager =
            LinearLayoutManager(this@HomeFragment.context, RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest { loadStates ->
                progressBar.isVisible = loadStates.refresh is LoadState.Loading
            }
        }

        searchArticle()
    }

    override fun observeData() {
        viewModel.homeStateLiveData.observe(this) {
            when (it) {
                is HomeState.GetArticles -> {
                    handleGetArticles(it.flow)
                }
                else -> {}
            }
        }
    }

    private fun handleGetArticles(flow: Flow<PagingData<ArticleModel>>) {
        lifecycleScope.launchWhenStarted {
            flow.collect {
                pagingAdapter.submitData(it)
            }
        }
    }

    private fun searchArticle() = with(binding) {
        var startTime = System.currentTimeMillis()
        var endTime: Long

        binding.etSearch.text =
            Editable.Factory.getInstance().newEditable(viewModel.searchTextWatcher)

        etSearch.addTextChangedListener { text: Editable? ->
            endTime = System.currentTimeMillis()

            if (endTime - startTime > SEARCH_ARTICLES_TIME_DELAY) {
                text?.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()) {
                        viewModel.fetchData(query)
                        viewModel.searchTextWatcher = query
                    }
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
        viewModel.insertArticleInDB(model)
        context?.let { it.toast(resources.getString(R.string.toast_clip_article)) }
    }
}