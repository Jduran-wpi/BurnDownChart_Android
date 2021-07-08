package com.example.burnitdownpres.database

/**
 * ProjectWithTasks.kt
 * @author Humberto Martinez
 * Description: Dataclass to establish relationship between Projects and Tasks
 */
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProjectWithTasks(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "projectID",
        entityColumn = "projectID"
    )
    var tasks: List<Task>
):Parcelable