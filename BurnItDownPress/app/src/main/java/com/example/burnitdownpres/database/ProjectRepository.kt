package com.example.burnitdownpres.database

/**
 * ProjectRepository.kt
 * @author Humberto Martinez
 * Description: Class that acts as an intermediary between DAO and ViewModel
 */

import androidx.lifecycle.LiveData

class ProjectRepository(private val projectDatabaseDao: ProjectDatabaseDao) {

    val getAllProjects: LiveData<List<ProjectWithTasks>> = projectDatabaseDao.getIncompleteProjects()
    val getDoneProjects: LiveData<List<ProjectWithTasks>> = projectDatabaseDao.getCompleteProjects()

    suspend fun addProject(project: Project){
        projectDatabaseDao.insert(project)
    }

    suspend fun update(project: Project){
        projectDatabaseDao.update(project)
    }

    suspend fun addTask(task: Task){
        projectDatabaseDao.insertTask(task)
    }

    suspend fun updateTask(task: Task){
        projectDatabaseDao.updateTask(task)
    }
}