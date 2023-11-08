package com.yhj.gdscandroidstudy.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yhj.gdscandroidstudy.data.user.UserRepository
import com.yhj.gdscandroidstudy.util.SUBSCRIPTION_TIMEOUT
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyPageViewModel(userRepository: UserRepository) : ViewModel() {

    val name = userRepository.userNameFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT),
        initialValue = "",
    )

    val eventFlow = MutableSharedFlow<Event>()

    fun onClickEdit() {
        viewModelScope.launch {
            eventFlow.emit(Event.EditClicked)
        }
    }
}

sealed class Event {
    data object EditClicked : Event()
}
