package com.example.burnitdownpres.database

/**
 * ProjectDatabaseDao
 * @author Humberto Martinez
 * Description: Database interface object
 * Run queries to database on this class
 */

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface ProjectDatabaseDao {

    @Insert
    suspend fun insert(project: Project)

    @Update
    suspend fun update(project: Project)

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM project_list_table WHERE projectID = :key")
    suspend fun get(key: Long): Project?

    @Query("DELETE FROM project_list_table")
    suspend fun clear()

    @Transaction
    @Query("SELECT * FROM project_list_table ORDER BY projectID DESC")
    fun getAllProjects(): LiveData<List<Project>>

    @Transaction
    @Query("SELECT * FROM project_list_table WHERE is_complete = 0 ORDER BY projectID DESC")
    fun getIncompleteProjects(): LiveData<List<ProjectWithTasks>>

    @Transaction
    @Query("SELECT * FROM project_list_table WHERE is_complete = 1 ORDER BY projectID DESC")
    fun getCompleteProjects(): LiveData<List<ProjectWithTasks>>

    @Query("SELECT * FROM project_list_table WHERE projectID =:key")
    fun getProjectWithID(key: Long): LiveData<Project>

    @Query("SELECT * FROM task_list_table WHERE projectID = :key AND is_completed = 1")
    fun getCompletedTasksOfProject(key: Long): LiveData<List<Task>>

    @Query("SELECT * FROM task_list_table WHERE projectID = :key AND is_completed = 0")
    fun getIncompletedTasksOfProject(key: Long): LiveData<List<Task>>

    @Query("SELECT * FROM task_list_table ORDER BY taskID DESC")
    fun getTasks(): LiveData<List<Task>>


//    @Transaction
//    @Query("SELECT * FROM project_list_table WHERE projectID =:key ORDER BY projectID DESC")
//    fun getProjectsWithTasks(key: Long): LiveData<List<ProjectWithTasks>>




}