package gr.aytn.todoapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.aytn.todoapp.TaskRepository
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


    val readAllData: LiveData<List<Task>> = repository.readAllData
    val readAllCompleted: LiveData<List<Task>> = repository.readAllCompleted
    val readAllTodaysTasks: LiveData<List<Task>> = repository.readAllTodaysTasks("$day ${MONTHS[month]} $year")
    fun searchResults(title: String): LiveData<List<Task>> = repository.searchTask(title.lowercase())
    fun searchByTitleResults(title: String): LiveData<Task> = repository.searchByTitle(title)
    val count: LiveData<Int> = repository.count
    val completedCount: LiveData<Int> = repository.completedCount


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
    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) {
        viewModelScope.launch {
            repository.updateTask(task.copy(completed = isChecked))
        }
    }
    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
    fun deleteAllCompleted(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCompleted()
        }
    }
}