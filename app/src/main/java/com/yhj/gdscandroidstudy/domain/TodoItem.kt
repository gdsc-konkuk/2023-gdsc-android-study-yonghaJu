package com.yhj.gdscandroidstudy.domain

data class TodoItem(
    val id: Long,
    val title: String,
    val isCompleted: Boolean = false,
) {
    companion object {
        val DUMMY = listOf(
            TodoItem(1, "todo1~~~", true),
            TodoItem(2, "todo2~~~", false),
            TodoItem(3, "todo3~~~", true),
        )
    }
}
