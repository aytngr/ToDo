package gr.aytn.todoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.aytn.todoapp.repository.TaskRepository
import gr.aytn.todoapp.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository: TaskRepository,): ViewModel(){
    val myCalendar: Calendar = Calendar.getInstance()
    val year = myCalendar.get(Calendar.YEAR)
    val month = myCalendar.get(Calendar.MONTH)
    val day = myCalendar.get(Calendar.DAY_OF_MONTH)

    val MONTHS: ArrayList<String> = arrayListOf("January","February","March","April","May","June","July","August","September","October","November","December")


//    fun findTaskById(task_id:Int): LiveData<Task> = repository.findTaskById(task_id)

    //    fun searchResults(title: String): LiveData<List<Task>> = repository.searchTask(title.lowercase())
//    val readAllTodaysTasks: LiveData<List<Task>> = repository.readAllTodaysTasks("$day ${MONTHS[month]} $year")
//    val readAllCompleted: LiveData<List<Task>> = repository.readAllCompleted
//    val readAllData: LiveData<List<Task>> = repository.readAllData

    fun userTasksCount(user_id: Int): LiveData<Int> = repository.getUserTasksCount(user_id)
    fun userCompletedTasksCount(user_id: Int): LiveData<Int> = repository.getUserCompletedTasksCount(user_id)

    fun getUserTodaysTasks(user_id: Int): LiveData<List<Task>> = repository.getUserTodaysTasks(user_id, "$day ${MONTHS[month]} $year")


    fun getUserTasks(user_id: Int): LiveData<List<Task>> = repository.getUserTasks(user_id)
    fun getUserCompletedTasks(user_id: Int): LiveData<List<Task>> = repository.getUserCompletedTasks(user_id)

    fun searchByTitleResults(title: String): LiveData<Task> = repository.searchByTitle(title)
    fun searchByTitleInsensitive(userId: Int, title:String): LiveData<List<Task>> = repository.searchByTitleInsensitive(userId,title)


    fun addTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }
    fun deleteAllCompleted(userId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCompleted(userId)
        }
    }
    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) {
        viewModelScope.launch {
            repository.updateTask(task.copy(completed = isChecked))
        }
    }
//    fun deleteAll(){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteAll()
//        }
//    }
//    fun deleteAllCompleted(){
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteAllCompleted()
//        }
//    }
//    fun getLastCustomerId(): LiveData<Int> {
//        return repository.getLastCustomerId()
//    }
}