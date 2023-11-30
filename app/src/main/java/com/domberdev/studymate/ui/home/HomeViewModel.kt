package com.domberdev.studymate.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domberdev.studymate.data.StudyMateRepository
import com.domberdev.studymate.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: StudyMateRepository
) : ViewModel() {

    val incompleteTaskList = MutableLiveData<List<Task>>()
    val completeTaskList = MutableLiveData<List<Task>>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            incompleteTaskList.postValue(repository.getIncompleteTaskFromDatabase())
            completeTaskList.postValue(repository.getCompleteTaskFromDatabase())
            isLoading.postValue(false)
        }
    }
}