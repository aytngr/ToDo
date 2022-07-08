package gr.aytn.todoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.aytn.todoapp.repository.UserRepository
import gr.aytn.todoapp.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository,): ViewModel() {

//    val readAllUser: LiveData<List<User>> = repository.readAllUser
    fun findByEmail(email:String): LiveData<User> = repository.findByEmail(email)
    fun getUserById(user_id: Int): LiveData<User> = repository.getUserById(user_id)



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

//    fun addUserTask(userTask: UserTask) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addUserTask(userTask)
//        }
//    }
//    fun onTaskCheckedChanged(user: User) {
//        viewModelScope.launch {
//            repository.updateUser(user.copy(completed = isChecked))
//        }
//    }
}