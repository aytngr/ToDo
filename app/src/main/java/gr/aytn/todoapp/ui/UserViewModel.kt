package gr.aytn.todoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.aytn.todoapp.TaskRepository
import gr.aytn.todoapp.model.Task
import gr.aytn.todoapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: TaskRepository,): ViewModel() {

    val readAllUser: LiveData<List<User>> = repository.readAllUser
    fun findByEmail(email:String): LiveData<User> = repository.findByEmail(email)

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }
//    fun onTaskCheckedChanged(user: User) {
//        viewModelScope.launch {
//            repository.updateUser(user.copy(completed = isChecked))
//        }
//    }
}