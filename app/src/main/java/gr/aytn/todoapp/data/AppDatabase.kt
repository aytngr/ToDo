package gr.aytn.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import gr.aytn.todoapp.model.Task
import gr.aytn.todoapp.model.User
import gr.aytn.todoapp.model.UserWithTask

@Database(entities = [Task::class, User::class], version = 16  )
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun userDao(): UserDao
}