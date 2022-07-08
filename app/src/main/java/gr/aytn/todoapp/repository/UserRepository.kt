package gr.aytn.todoapp.repository

import androidx.lifecycle.LiveData
import gr.aytn.todoapp.data.TaskDao
import gr.aytn.todoapp.data.UserDao
import gr.aytn.todoapp.model.User
import javax.inject.Inject

class UserRepository@Inject constructor(private val userDao: UserDao) {

    val readAllUser: LiveData<List<User>> = userDao.getAllUsers()

    fun findByEmail(email: String): LiveData<User> = userDao.findByEmail(email)
    fun getUserById(user_id: Int): LiveData<User> = userDao.getUserById(user_id)

    fun addUser(user: User) {
        userDao.addUser(user)
    }

    fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}