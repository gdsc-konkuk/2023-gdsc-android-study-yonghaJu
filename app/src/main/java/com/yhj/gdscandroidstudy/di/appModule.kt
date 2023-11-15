package com.yhj.gdscandroidstudy.di // ktlint-disable filename

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yhj.gdscandroidstudy.data.AppDatabase
import com.yhj.gdscandroidstudy.data.AppDatabase.Companion.DATABASE_NAME
import com.yhj.gdscandroidstudy.data.photo.PhotoRepositoryImpl
import com.yhj.gdscandroidstudy.data.photo.PhotoService
import com.yhj.gdscandroidstudy.data.photo.UnSplashInterceptor
import com.yhj.gdscandroidstudy.data.todo.TodoDataSource
import com.yhj.gdscandroidstudy.data.todo.TodoLocalDataSource
import com.yhj.gdscandroidstudy.data.todo.TodoRepositoryImpl
import com.yhj.gdscandroidstudy.data.user.UserRepositoryImpl
import com.yhj.gdscandroidstudy.domain.PhotoRepository
import com.yhj.gdscandroidstudy.domain.SetRandomPhotoUseCase
import com.yhj.gdscandroidstudy.domain.TodoRepository
import com.yhj.gdscandroidstudy.domain.UserRepository
import com.yhj.gdscandroidstudy.ui.edit.EditViewModel
import com.yhj.gdscandroidstudy.ui.home.HomeViewModel
import com.yhj.gdscandroidstudy.ui.mypage.MyPageViewModel
import com.yhj.gdscandroidstudy.util.NETWORK
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    viewModel { EditViewModel(get(), get()) }
    viewModel { MyPageViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DATABASE_NAME,
        ).build()
    }
    single { get<AppDatabase>().todoDao() }
    single<TodoRepository> { TodoRepositoryImpl(get()) }
    single<TodoDataSource> { TodoLocalDataSource(get()) }
    single<PhotoRepository> { PhotoRepositoryImpl(get()) }
    single {
        OkHttpClient.Builder().addInterceptor(UnSplashInterceptor()).build()
    }
    single {
        val json = Json {
            ignoreUnknownKeys = true
        }
        Retrofit.Builder().baseUrl(NETWORK.UNSPLASH.BASE_URL).client(get()).addConverterFactory(
            json.asConverterFactory(NETWORK.UNSPLASH.MEDIA_TYPE.toMediaType()),
        ).build()
    }
    single { SetRandomPhotoUseCase(get(), get()) }
    single { get<Retrofit>().create(PhotoService::class.java) }
}
