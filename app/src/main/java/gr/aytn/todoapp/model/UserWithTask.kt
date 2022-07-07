package gr.aytn.todoapp.model

import androidx.room.*

data class UserWithTask (
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userCreatorId"
    )
    val tasks: List<Task>
)