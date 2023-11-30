package com.domberdev.studymate.data

import com.domberdev.studymate.data.database.dao.StudyMateDao
import com.domberdev.studymate.data.database.entity.TaskEntity
import com.domberdev.studymate.domain.model.Task
import com.domberdev.studymate.domain.model.toDomain
import javax.inject.Inject

class StudyMateRepository @Inject constructor(private val studyMateDao: StudyMateDao) {

    suspend fun getIncompleteTaskFromDatabase(): List<Task> {
        val response = studyMateDao.getIncompleteTask()
        return response.map { it.toDomain() }
    }

    suspend fun getCompleteTaskFromDatabase(): List<Task> {
        val response = studyMateDao.getCompleteTask()
        return response.map { it.toDomain() }
    }

    suspend fun getTaskByID(id: Long): Task {
        val response = studyMateDao.getTaskByID(id)
        return response.toDomain()
    }

    suspend fun updateTaskStatus(status: Boolean, id: Long) {
        studyMateDao.updateTaskStatus(status, id)
    }

    suspend fun insertTask(task: TaskEntity) {
        studyMateDao.insertTask(task)
    }

    suspend fun deleteTask(task: TaskEntity) {
        studyMateDao.deleteTask(task)
    }
}