package com.yhj.week1

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.UnknownServiceException

object ServiceLocator {
    inline fun <reified T> getService(): T {
        if (T::class == NameRepository::class) {
            return NameRepository as T
        }
        throw UnknownServiceException()
    }
}

object NameRepository {
    private val _name = MutableStateFlow("주용한")
    val name get() = _name.asStateFlow()

    fun setName(name: String) {
        _name.value = name
    }
}
