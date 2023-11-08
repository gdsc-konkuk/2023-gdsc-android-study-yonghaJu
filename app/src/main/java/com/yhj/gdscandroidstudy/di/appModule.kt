package com.yhj.gdscandroidstudy.di // ktlint-disable filename

import androidx.room.Room
import com.yhj.gdscandroidstudy.data.AppDatabase
import com.yhj.gdscandroidstudy.data.AppDatabase.Companion.DATABASE_NAME
import com.yhj.gdscandroidstudy.data.todo.TodoDataSource
import com.yhj.gdscandroidstudy.data.todo.TodoLocalDataSource
import com.yhj.gdscandroidstudy.data.todo.TodoRepositoryImpl
import com.yhj.gdscandroidstudy.data.user.UserRepository
import com.yhj.gdscandroidstudy.data.user.UserRepositoryImpl
import com.yhj.gdscandroidstudy.domain.TodoRepository
import com.yhj.gdscandroidstudy.ui.edit.EditViewModel
import com.yhj.gdscandroidstudy.ui.home.HomeViewModel
import com.yhj.gdscandroidstudy.ui.mypage.MyPageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    viewModel { EditViewModel(get()) }
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
}
