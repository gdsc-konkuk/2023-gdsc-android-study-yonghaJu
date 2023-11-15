package com.yhj.gdscandroidstudy.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yhj.gdscandroidstudy.domain.UserRepository
import com.yhj.gdscandroidstudy.util.SUBSCRIPTION_TIMEOUT
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditViewModel(private val userRepository: UserRepository) : ViewModel() {

    val name = userRepository.userNameFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT),
        initialValue = "",
    )

    val editableName = MutableStateFlow("")
    private val editableNameValue get() = editableName.value

    init {
        viewModelScope.launch {
            editableName.value = userRepository.userName()
        }
    }

    val eventFlow = MutableSharedFlow<Event>()
    fun saveName() {
        if (editableNameValue.isBlank()) {
            viewModelScope.launch {
                eventFlow.emit(Event.SavingFailed("닉네임은 빈칸일 수 없습니다."))
            }
        } else {
            viewModelScope.launch {
                userRepository.setName(editableNameValue)
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
