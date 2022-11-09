package com.hoon.newsarticleapp.module

import com.hoon.microdustapp.data.database.ArticleDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single { ArticleDataBase.build(androidApplication()) }

    single { get<ArticleDataBase>().articleDao() }
}