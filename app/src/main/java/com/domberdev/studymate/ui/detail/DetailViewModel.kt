package com.domberdev.studymate.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domberdev.studymate.data.StudyMateRepository
import com.domberdev.studymate.data.database.entity.toDatabase
import com.domberdev.studymate.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: StudyMateRepository
) : ViewModel() {

    val task = MutableLiveData<Task>()

    fun onCreate(id: Long) {
        viewModelScope.launch {
            task.postValue(repository.getTaskByID(id))
        }
    }

    fun completeTask(status: Boolean, id: Long) {
        viewModelScope.launch {
            repository.updateTaskStatus(status, id)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task.toDatabase())
        }
    }
}