package com.example.burnitdownpres.fragments.update

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.burnitdownpres.R
import com.example.burnitdownpres.database.Project
import com.example.burnitdownpres.database.Task
import kotlinx.android.synthetic.main.custom_task.view.*

/**
 * UpdateAdapter.kt
 * @author Humberto Martinez
 * Description: Inflates the custom_task.xml as a template for each task of a project
 */
class UpdateAdapter:  RecyclerView.Adapter<UpdateAdapter.MyViewHolder>() {
    private var taskList = emptyList<Task>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_task, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.itemView.taskName_txt.text = currentItem.title

        holder.itemView.taskLayout.setOnClickListener {
            val action = UpdateFragmentDirections.actionUpdateFragmentToUpdateTask(currentItem)
            holder.itemView.findNavController().navigate(action)
        }


    }

    override fun getItemCount(): Int {
        return taskList.size
    }
    fun setData( task: List<Task>){
        this.taskList = task
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}