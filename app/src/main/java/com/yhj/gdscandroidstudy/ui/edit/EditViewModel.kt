package com.yhj.gdscandroidstudy.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yhj.gdscandroidstudy.data.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditViewModel(private val userRepository: UserRepository) : ViewModel() {

    val name = userRepository.userNameFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        "",
    )

    val editableName = MutableStateFlow("")

    init {
        viewModelScope.launch {
            editableName.value = userRepository.userName()
        }
    }

    val eventFlow = MutableSharedFlow<Event>()
    fun saveName() {
        if (editableName.value.isBlank()) {
            viewModelScope.launch {
                eventFlow.emit(Event.SavingFailed("닉네임은 빈칸일 수 없습니다."))
            }
        } else {
            viewModelScope.launch {
                userRepository.setName(editableName.value)
                eventFlow.emit(Event.SavingSuccess("닉네임 저장 성공."))
            }
        }
    }

    fun clickBackButton() {
        viewModelScope.launch {
            eventFlow.emit(Event.BackButtonClicked)
        }
    }
}

sealed class Event {
    data class SavingFailed(val message: String) : Event()
    data class SavingSuccess(val message: String) : Event()
    data object BackButtonClicked : Event()
}
