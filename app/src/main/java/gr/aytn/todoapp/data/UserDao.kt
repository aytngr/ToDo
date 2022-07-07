package gr.aytn.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import gr.aytn.todoapp.model.User
import gr.aytn.todoapp.model.UserWithTask

@Dao
interface UserDao {
    //User

    @Query("SELECT * FROM user WHERE id LIKE :userId")
    suspend fun getUserById(userId: String): User?

//    @Transaction
//    @Query("SELECT * FROM user WHERE id = :id")
//    suspend fun getUserWithNotesById(id: Int): UserWithTask

    @Insert(onConflict = OnConflictStrategy.REPLACE) // <- Annotate the 'addUser' function below. Set the onConflict strategy to IGNORE so if exactly the same user exists, it will just ignore it.
    fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM user ORDER BY id DESC")
    fun getAllUsers(): LiveData<List<User>>
//
    @Query("SELECT * FROM user where email LIKE  :email")
    fun findByEmail(email: String): LiveData<User>


}