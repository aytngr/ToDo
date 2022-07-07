package gr.aytn.todoapp

import androidx.lifecycle.LiveData
import gr.aytn.todoapp.data.TaskDao
import gr.aytn.todoapp.model.Task
import gr.aytn.todoapp.model.User
import java.util.*
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {


//    val completedCount: LiveData<Int> = taskDao.getCompletedCount()
//    val readAllData: LiveData<List<Task>> = taskDao.getAll()
//    val readAllCompleted: LiveData<List<Task>> = taskDao.getCompleted()
//    fun readAllTodaysTasks(todaysDate: String): LiveData<List<Task>> = taskDao.getTodaysTasks(todaysDate)
//    fun searchTask(title: String): LiveData<List<Task>> = taskDao.search(title)
//    fun findTaskById(task_id:Int): LiveData<Task> = taskDao.findTaskById(task_id)

    fun getUserTasks(user_id: Int): LiveData<List<Task>> = taskDao.getUserTasks(user_id)
    fun getUserCompletedTasks(user_id: Int): LiveData<List<Task>> = taskDao.getUserCompletedTasks(user_id)

    fun getUserTodaysTasks(user_id: Int, todaysDate: String): LiveData<List<Task>> = taskDao.getUserTodaysTasks(user_id, todaysDate)

    fun getUserTasksCount(user_id: Int): LiveData<Int> = taskDao.getUserTasksCount(user_id)
    fun getUserCompletedTasksCount(user_id: Int): LiveData<Int> = taskDao.getUserCompletedTasksCount(user_id)

    fun searchByTitle(title:String): LiveData<Task> = taskDao.searchByTitle(title)
    fun searchByTitleInsensitive(userId: Int, title:String): LiveData<List<Task>> = taskDao.searchByTitleInsensitive(userId,title)

    fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun deleteAllCompleted(userId: Int) {
        taskDao.deleteAllCompleted(userId)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
//    fun deleteAll() {
//        taskDao.deleteAll()
//    }
//    fun deleteAllCompleted() {
//        taskDao.deleteAllCompleted()
//    }
//    fun getLastCustomerId(): LiveData<Int> {
//        return taskDao.getLastCustomerId()
//    }


}