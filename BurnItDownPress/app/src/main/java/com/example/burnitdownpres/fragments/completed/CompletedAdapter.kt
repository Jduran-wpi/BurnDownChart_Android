package com.example.burnitdownpres.fragments.completed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.burnitdownpres.R
import com.example.burnitdownpres.database.Project
import com.example.burnitdownpres.database.ProjectWithTasks
import kotlinx.android.synthetic.main.custom_row.view.*

/**
 * CompletedAdapter.kt
 * @author Jonathan Duran
 * @author Humberto Martinez
 * Description: Adapter based off ListAdapter.kt
 * Uses same xml as ListAdapter to display Completed Projects
 */

class CompletedAdapter: RecyclerView.Adapter<CompletedAdapter.MyViewHolder>() {

    //Variables
    private var projectList = emptyList<ProjectWithTasks>()

    //Methods
    //-------------------------------------------------------------------------------------------------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }
    //------------------------------------------------------------------------------------------------
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = projectList[position]
        holder.itemView.projName_txt.text = currentItem.project.title

    }
    //------------------------------------------------------------------------------------------------
    override fun getItemCount(): Int {

        return projectList.size
    }
    //------------------------------------------------------------------------------------------------
    fun setData( project: List<ProjectWithTasks>){
        this.projectList = project
        notifyDataSetChanged()
    }
    //------------------------------------------------------------------------------------------------
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
    //----------------------------------------------------------------------------------------------------
}