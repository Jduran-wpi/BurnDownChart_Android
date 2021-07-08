package com.example.burnitdownpres.database

/**
 * TaskViewModel.kt
 * @author Humberto Martinez
 * Description: Custom View Model to get the unique List of
 * Tasks per Project
 */

import android.app.Application
import androidx.lifecycle.*
import com.example.burnitdownpres.database.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(projectDatabaseDao: ProjectDatabaseDao, key: Long): ViewModel(){
    var readAllTasks: LiveData<List<Task>>
    var readDoneTasks: LiveData<List<Task>>
    private val repository: TaskRepository = TaskRepository(projectDatabaseDao)

    init {
        readAllTasks = repository.getAllTaskList(key)
        readDoneTasks = repository.getDoneTaskList(key)
    }

}