package com.yhj.gdscandroidstudy.data.todo

import com.yhj.gdscandroidstudy.domain.TodoItem

object Mapper {
    fun toTodoEntity(todoItem: TodoItem) =
        TodoEntity(
            id = todoItem.id,
            title = todoItem.title,
            isCompleted = todoItem.isCompleted,
        )

    fun toTodoItem(todoEntity: TodoEntity) =
        TodoItem(
            id = todoEntity.id,
            title = todoEntity.title,
            isCompleted = todoEntity.isCompleted,
        )
}
