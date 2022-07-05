package gr.aytn.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import gr.aytn.todoapp.model.Task

@Database(entities = [Task::class], version = 8)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}