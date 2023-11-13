package com.yhj.gdscandroidstudy

import android.app.Application
import com.yhj.gdscandroidstudy.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class GDSCApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GDSCApplication)
            modules(appModule)
        }
    }
}
