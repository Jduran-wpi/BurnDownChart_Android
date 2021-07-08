package com.example.burnitdownpres.database

/**
 * TaskViewModelFactory.kt
 * @author Humberto Martinez
 * Description: ViewModelFactory to create the custom ViewModel needed
 * To get the individual tasks of a Project
 */

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskViewModelFactory( private val projectDatabaseDao: ProjectDatabaseDao, private val key: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
             return TaskViewModel(projectDatabaseDao,key) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}