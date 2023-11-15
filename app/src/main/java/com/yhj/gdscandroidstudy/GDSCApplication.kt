package com.yhj.gdscandroidstudy

import android.app.Application
import com.yhj.gdscandroidstudy.di.dataModule
import com.yhj.gdscandroidstudy.di.databaseModule
import com.yhj.gdscandroidstudy.di.networkModule
import com.yhj.gdscandroidstudy.di.useCaseModule
import com.yhj.gdscandroidstudy.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class GDSCApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GDSCApplication)
            modules(viewModelModule)
            modules(dataModule)
            modules(useCaseModule)
            modules(databaseModule)
            modules(networkModule)
        }
    }
}
