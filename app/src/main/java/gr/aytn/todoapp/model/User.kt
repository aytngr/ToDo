package gr.aytn.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import gr.aytn.todoapp.R

@Entity
data class User (
        @ColumnInfo(name = "name") val name: String?,
        @ColumnInfo(name = "email") val email: String?,
        @ColumnInfo(name = "password") val password: String?,
        @ColumnInfo(name = "image") var image: String?,
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Int = 0)
