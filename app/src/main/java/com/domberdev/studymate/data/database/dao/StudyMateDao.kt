package com.domberdev.studymate.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.domberdev.studymate.data.database.entity.TaskEntity

@Dao
interface StudyMateDao {

    @Query("SELECT * FROM task_table ORDER BY deadline ASC")
    suspend fun getAllTask(): List<TaskEntity>

    @Query("SELECT * FROM task_table WHERE id = :id")
    suspend fun getTaskByID(id: Long): TaskEntity

    @Query("UPDATE task_table SET status=:status WHERE id = :id")
    suspend fun updateTaskStatus(status: Boolean, id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}