package com.example.burnitdownpres.database

/**
 * ProjectDatabase.kt
 * @author Humberto Martinez
 * Description: Initializes the database for the entire app. Constructs the empty
 * tables of each data class
 */

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Project::class, Task::class], version = 1, exportSchema = false)
abstract class ProjectDatabase: RoomDatabase(){
    abstract fun projectDatabaseDao(): ProjectDatabaseDao



    companion object{

        @Volatile
        private var INSTANCE: ProjectDatabase? = null

        fun getDataBase(context: Context): ProjectDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProjectDatabase::class.java,
                    "project_history_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}