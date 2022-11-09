package com.hoon.newsarticleapp

import android.app.Application
import com.hoon.newsarticleapp.module.appModule
import com.hoon.newsarticleapp.module.databaseModule
import com.hoon.newsarticleapp.module.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NewsArticleApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger(Level.ERROR) // koin의 로그 레벨을 지정
            } else {
                androidLogger(Level.NONE)
            }
            androidContext(this@NewsArticleApplication) // context 등록
            modules(appModule + retrofitModule + databaseModule)
        }
    }
}