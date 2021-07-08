package com.example.burnitdownpres.fragments.update.addTask

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.burnitdownpres.R
import com.example.burnitdownpres.database.ProjectViewModel
import com.example.burnitdownpres.database.Task
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.fragment_add_task.view.*

/**
 * addTaskFragment.kt
 * @author Humberto Martinez
 *
 * Description: UI Fragment to convert textbox fields from UI
 * into a task that gets added using the appropriate viewmodel
 */



class addTaskFragment : Fragment() {

    private val args by navArgs<addTaskFragmentArgs>()

    private lateinit var mProjectViewModel: ProjectViewModel
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_task, container, false)


        mProjectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
        view.addtask_btn.setOnClickListener{
            insertDataToDataBase()
        }
        return view
    }
    private fun insertDataToDataBase(){
        val taskName = addTaskName.text.toString()
        val taskPrior = addTaskPriority.text
        val projectid = args.currentProject.projectID

        if ( inputCheck(taskName,taskPrior)){
            //Create Task
            val task = Task(0,projectid, taskName,Integer.parseInt(projectid.toString()),false)
            //Add Task to database

            mProjectViewModel.addTask(task)
            Toast.makeText(requireContext(),"Added Task Successfully", Toast.LENGTH_SHORT).show()
            //Navigate Back
            //findNavController().navigate(R.id.action_addTaskFragment_to_updateFragment)
        }else{
            Toast.makeText(requireContext(), "Please Enter a Task Correctly", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck( taskName: String, taskPrior: Editable): Boolean{
        return !(TextUtils.isEmpty(taskName) || taskPrior.isEmpty())
    }

}