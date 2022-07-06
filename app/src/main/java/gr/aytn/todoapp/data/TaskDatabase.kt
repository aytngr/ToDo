package gr.aytn.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import gr.aytn.todoapp.model.Task
import gr.aytn.todoapp.model.User

@Database(entities = [Task::class, User::class], version = 10)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}