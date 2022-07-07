package gr.aytn.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import gr.aytn.todoapp.model.Task
import gr.aytn.todoapp.model.User
import gr.aytn.todoapp.model.UserWithTask

//import gr.aytn.todoapp.model.UserTask


@Dao
interface TaskDao {

//    @Transaction
//    @Query("SELECT * FROM user")
//    fun getUsersWithTasks(): List<UserWithTask>

    @Query("SELECT * FROM task WHERE userCreatorId = :userId ORDER BY id DESC")
    fun getUserTasks(userId: Int): LiveData<List<Task>>

    @Query("SELECT COUNT(*) FROM task WHERE userCreatorId = :userId")
    fun getUserTasksCount(userId: Int): LiveData<Int>

    @Query("SELECT COUNT(*) FROM task WHERE userCreatorId = :userId AND completed = 1")
    fun getUserCompletedTasksCount(userId: Int): LiveData<Int>

    @Query("SELECT * FROM task WHERE userCreatorId = :userId AND completed = 1")
    fun getUserCompletedTasks(userId: Int): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE title = :title")
    fun searchByTitle(title: String): LiveData<Task>

    @Query("SELECT * FROM task WHERE userCreatorId = :userId AND date = :todaysDate ORDER BY id DESC")
    fun getUserTodaysTasks(userId: Int, todaysDate: String): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE userCreatorId = :userId AND lower(title) LIKE '%' || :title || '%' ORDER BY id DESC")
    fun searchByTitleInsensitive(userId: Int, title: String): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE FROM task WHERE userCreatorId = :userId AND completed = 1")
    fun deleteAllCompleted(userId: Int)

//
//    @Query("SELECT * FROM task ORDER BY id DESC")
//    fun getAll(): LiveData<List<Task>>
//
//    @Query("SELECT id FROM task ORDER BY id DESC LIMIT 1")
//    fun getLastCustomerId(): LiveData<Int>

//    @Query("SELECT * FROM usertask WHERE user_id = :user_id")
//    fun getUserTask(user_id: Int): LiveData<List<UserTask>>

//    @Query("SELECT * FROM task WHERE id = :task_id")
//    fun findTaskById(task_id: Int): LiveData<Task>
//

//
//    @Query("SELECT * FROM task WHERE title = :title")
//    fun searchByTitle(title: String): LiveData<Task>
//
//    @Query("SELECT * FROM task WHERE completed = 1 ")
//    fun getCompleted(): LiveData<List<Task>>
//

//
//    @Query("SELECT COUNT(*) FROM task WHERE completed = 1")
//    fun getCompletedCount(): LiveData<Int>
//
//    @Query("SELECT COUNT(*) FROM task")
//    fun count(): LiveData<Int>
//
//    @Query("DELETE FROM task")
//    fun deleteAll()
//

//
//    @Insert(onConflict = OnConflictStrategy.IGNORE) // <- Annotate the 'addUser' function below. Set the onConflict strategy to IGNORE so if exactly the same user exists, it will just ignore it.
//    fun addTask(task: Task)
//
//    @Update
//    suspend fun updateTask(task: Task)
//
//    @Delete
//    fun deleteTask(task: Task)



    //UserTask

//    @Insert(onConflict = OnConflictStrategy.IGNORE) // <- Annotate the 'addUser' function below. Set the onConflict strategy to IGNORE so if exactly the same user exists, it will just ignore it.
//    fun addUserTask(userTask: UserTask)



}