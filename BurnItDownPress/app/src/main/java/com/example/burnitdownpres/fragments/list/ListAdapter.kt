package com.example.burnitdownpres.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.burnitdownpres.R
import com.example.burnitdownpres.database.Project
import com.example.burnitdownpres.database.ProjectWithTasks
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.fragment_add.view.*

/**
 * ListAdapter.kt
 * @author Humberto Martinez
 * Description: Adapter to inlfate custom_row.xml for each Project that is incomplete
 * Uses the custom_row.xml as a template and adapts each Project to fit inside
 * Then uses a setOnCLick Listener to send it to the UpdateFragment to modify details
 * of the project and view it's tasks
 */

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {


    private var projectList = emptyList<ProjectWithTasks>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = projectList[position]
        holder.itemView.projName_txt.text = currentItem.project.title

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    fun setData( project: List<ProjectWithTasks>){
        this.projectList = project
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}