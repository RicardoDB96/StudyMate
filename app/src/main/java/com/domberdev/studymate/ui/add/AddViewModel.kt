package com.domberdev.studymate.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domberdev.studymate.data.StudyMateRepository
import com.domberdev.studymate.data.database.entity.toDatabase
import com.domberdev.studymate.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repository: StudyMateRepository
) : ViewModel() {

    fun saveData(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task.toDatabase())
        }
    }
}