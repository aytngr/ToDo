package gr.aytn.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
        @ColumnInfo(name = "name") val name: String?,
        @ColumnInfo(name = "email") val email: String?,
        @ColumnInfo(name = "password") val password: String?,
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Int = 0)
