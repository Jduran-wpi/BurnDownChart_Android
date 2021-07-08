package com.example.burnitdownpres.database

/**
 * Task.kt
 * @author Humberto Martinez
 * Description: Dataclass to hold properties of a task
 */

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "task_list_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskID: Long = 0L,

    var projectID: Long,

    @ColumnInfo(name = "task_name")
    var title: String? = null,

    @ColumnInfo(name = "priority")
    var priority: Int? = null,

    @ColumnInfo(name = "is_completed")
    var isComplete: Boolean? = null,

    @ColumnInfo(name = "day_completed")
    var dayCompleted: Int? = null

): Parcelable