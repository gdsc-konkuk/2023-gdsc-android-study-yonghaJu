package com.yhj.gdscandroidstudy.di // ktlint-disable filename

import com.yhj.gdscandroidstudy.data.UserRepository
import com.yhj.gdscandroidstudy.ui.edit.EditViewModel
import com.yhj.gdscandroidstudy.ui.home.HomeViewModel
import com.yhj.gdscandroidstudy.ui.mypage.MyPageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { UserRepository(get()) }
    viewModel { EditViewModel(get()) }
    viewModel { MyPageViewModel(get()) }
    viewModel { HomeViewModel() }
}
