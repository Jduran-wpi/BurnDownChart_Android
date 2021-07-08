package com.example.burnitdownpres.database

/**
 * ProjectViewModel.kt
 * @author Humberto Martinez
 * Description: Class to hold data of application for UI to use
 * UI Fragments can run Observers on data held in this class
 */

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application):AndroidViewModel(application) {

    val readAllProjects: LiveData<List<ProjectWithTasks>>
    val readDoneProjects: LiveData<List<ProjectWithTasks>>
    val projectDatabaseDao: ProjectDatabaseDao
    private val repository: ProjectRepository


    init{
        projectDatabaseDao =  ProjectDatabase.getDataBase(application).projectDatabaseDao()
        repository = ProjectRepository(projectDatabaseDao)
        readAllProjects = repository.getAllProjects
        readDoneProjects = repository.getDoneProjects

    }
    fun addProject( project: Project){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProject(project)
        }
    }
    fun update(project: Project){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(project)
        }
    }

    fun addTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }
    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTask(task)
        }
    }
}