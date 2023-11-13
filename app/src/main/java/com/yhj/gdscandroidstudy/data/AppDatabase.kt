package com.yhj.gdscandroidstudy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yhj.gdscandroidstudy.data.todo.TodoDao
import com.yhj.gdscandroidstudy.data.todo.TodoEntity

@Database(entities = [TodoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        const val DATABASE_NAME = "database"
    }
}
