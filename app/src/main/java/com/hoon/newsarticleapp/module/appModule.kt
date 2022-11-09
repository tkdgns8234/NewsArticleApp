package com.hoon.newsarticleapp.module

import com.hoon.newsarticleapp.data.repository.article.ArticleRepository
import com.hoon.newsarticleapp.data.repository.article.ArticleRepositoryImpl
import com.hoon.newsarticleapp.data.repository.db.DatabaseRepository
import com.hoon.newsarticleapp.data.repository.db.DatabaseRepositoryImpl
import com.hoon.newsarticleapp.view.clip.ClipViewModel
import com.hoon.newsarticleapp.view.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { Dispatchers.IO }

    single<ArticleRepository> { ArticleRepositoryImpl(get()) }
    single<DatabaseRepository> { DatabaseRepositoryImpl(get(), get()) }

    viewModel { ClipViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
}