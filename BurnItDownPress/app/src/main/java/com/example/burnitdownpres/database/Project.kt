package com.example.burnitdownpres.database

/**
 * Project.kt
 * @author Humberto Martinez
 * Description: Class to hold properties of a Project
 */

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "project_list_table")
data class Project(
    @PrimaryKey(autoGenerate = true)
    var projectID: Long = 0L,

    @ColumnInfo(name = "project_name")
    var title: String? = null,

//        @ColumnInfo(name = "project_tasks")
//        var tasks: Task,

    @ColumnInfo(name = "days_to_complete")
    var daysToComplete: Int? = null,

    @ColumnInfo(name = "days_since_start")
    var daysSinceStart: Int? = null,

    @ColumnInfo(name = "number_of_tasks_remaining")
    var numberOfTasksRemaining: Int = -1,

    @ColumnInfo(name = "is_complete")
    var isComplete: Boolean? = false,

    @ColumnInfo(name = "is_started")
    var isStarted: Boolean? = true,

    @ColumnInfo(name = "is_behind")
    var isBehind: Boolean?= false,

    @ColumnInfo(name = "is_ahead")
    var isAhead: Boolean? = null
): Parcelable


