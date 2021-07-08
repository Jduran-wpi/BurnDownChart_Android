package com.example.burnitdownpres.fragments.update.taskupdate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.burnitdownpres.R
import com.example.burnitdownpres.database.ProjectViewModel
import com.example.burnitdownpres.database.Task
import kotlinx.android.synthetic.main.fragment_update_task.*
import kotlinx.android.synthetic.main.fragment_update_task.view.*

/**
 * UpdateTask.kt
 * @author Humberto Martinez
 * Description: UI Fragment to convert an Incomplete Task into a Completed one
 * Uses ViewModel to update the Task after a user has marked it complete
 */


class UpdateTask : Fragment() {

    private val args by navArgs<UpdateTaskArgs>()

    private lateinit var mProjectViewModel: ProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_update_task, container, false)


        mProjectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)

        view.completeTask_btn.setOnClickListener{
            completeTask()
        }

        return view


    }
    private fun completeTask(){
        val completedDay = Integer.parseInt(dayCompleted.text.toString())
        val updatedTask = Task(args.currentTask.taskID,args.currentTask.projectID,args.currentTask.title,args.currentTask.priority, true, completedDay)
        mProjectViewModel.updateTask(updatedTask)

        Toast.makeText(requireContext(),"Task Completed!", Toast.LENGTH_SHORT).show()
        //
    }


}