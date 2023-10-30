package com.yhj.week1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class EditViewModel : ViewModel() {

    val name = ServiceLocator.getService<NameRepository>().name
    val editableName = MutableStateFlow(name.value)

    val eventFlow = MutableSharedFlow<Event>()
    fun saveName() {
        if (editableName.value.isBlank()) {
            viewModelScope.launch {
                eventFlow.emit(Event.SavingFailed("닉네임은 빈칸일 수 없습니다."))
            }
        } else {
            ServiceLocator.getService<NameRepository>().setName(editableName.value)
            viewModelScope.launch {
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
