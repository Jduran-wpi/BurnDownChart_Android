package com.example.burnitdownpres.database

/**
 * TaskRepository.kt
 * @author Humberto Martinez
 *
 */

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TaskRepository(private val projectDatabaseDao: ProjectDatabaseDao) {

    var getAllTasks: LiveData<List<Task>> = projectDatabaseDao.getTasks()
    var getDoneTasks: LiveData<List<Task>> = projectDatabaseDao.getTasks()

    fun updateTaskList(key: Long){
        getAllTasks = projectDatabaseDao.getIncompletedTasksOfProject(key)
        getDoneTasks = projectDatabaseDao.getCompletedTasksOfProject(key)

    }

    fun getAllTaskList(key: Long): LiveData<List<Task>>{
        return projectDatabaseDao.getIncompletedTasksOfProject(key)
    }
    fun getDoneTaskList(key: Long): LiveData<List<Task>>{
        return projectDatabaseDao.getCompletedTasksOfProject(key)
    }
}