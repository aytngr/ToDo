package gr.aytn.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import gr.aytn.todoapp.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY id DESC")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE lower(title) LIKE '%' || :title || '%' ORDER BY id DESC")
    fun search(title: String): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE completed = 1 ")
    fun getCompleted(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE date = :todaysDate ORDER BY id DESC")
    fun getTodaysTasks(todaysDate: String): LiveData<List<Task>>

    @Query("SELECT COUNT(*) FROM task WHERE completed = 1")
    fun getCompletedCount(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM task")
    fun count(): LiveData<Int>

    @Query("DELETE FROM task")
    fun deleteAll()

    @Query("DELETE FROM task WHERE completed = 1")
    fun deleteAllCompleted()

    @Insert(onConflict = OnConflictStrategy.IGNORE) // <- Annotate the 'addUser' function below. Set the onConflict strategy to IGNORE so if exactly the same user exists, it will just ignore it.
    fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
}