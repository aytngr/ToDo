package gr.aytn.todoapp

import androidx.lifecycle.LiveData
import gr.aytn.todoapp.data.TaskDao
import gr.aytn.todoapp.model.Task
import java.util.*
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val myCalendar: Calendar = Calendar.getInstance()

    val readAllData: LiveData<List<Task>> = taskDao.getAll()
    val readAllCompleted: LiveData<List<Task>> = taskDao.getCompleted()
    fun readAllTodaysTasks(todaysDate: String): LiveData<List<Task>> = taskDao.getTodaysTasks(todaysDate)
    fun searchTask(title: String): LiveData<List<Task>> = taskDao.search(title)
    val count: LiveData<Int> = taskDao.count()
    val completedCount: LiveData<Int> = taskDao.getCompletedCount()

    fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
    fun deleteAll() {
        taskDao.deleteAll()
    }
    fun deleteAllCompleted() {
        taskDao.deleteAllCompleted()
    }

}