package com.example.burnitdownpres.fragments.add

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
import com.example.burnitdownpres.R
import com.example.burnitdownpres.database.Project
import com.example.burnitdownpres.database.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

/**
 * addFragment.kt
 * @author Humberto Martinez
 * Description: UI Fragment class. Code inflates xml document fragment_add.
 * Sets listeners to UI buttons and calls appropriate ViewModelFunctions to
 * send UI data to database
 */

class addFragment : Fragment() {


    private lateinit var mProjectViewModel: ProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        //Initialize ViewModel
        mProjectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)

        //Button OnClickListener
        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase(){
        val projName = addProjName.text.toString()
        val projLength = addProjLength.text

        if(inputCheck(projName,projLength)){
            //Create Project
            val project = Project(0,projName,Integer.parseInt(projLength.toString()))
            //Add project to database
            mProjectViewModel.addProject(project)
            Toast.makeText(requireContext(),"Added Project Successfully", Toast.LENGTH_SHORT).show()

            //Navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please Enter a Project Correctly",Toast.LENGTH_LONG).show()
        }
    }

    //Checks if TextBoxes are empty
    private fun inputCheck( projName: String, projLength: Editable): Boolean{
        return !(TextUtils.isEmpty(projName) || projLength.isEmpty())
    }


}