package com.yhj.week2.domain

data class TodoItem(
    val id: Long,
    val title: String,
    val isCompleted: Boolean = false,
) {
    companion object {
        val DUMMY = listOf(
            TodoItem(0, "todo1~~~"),
            TodoItem(1, "todo2~~~"),
            TodoItem(2, "todo3~~~"),
        )
    }
}
