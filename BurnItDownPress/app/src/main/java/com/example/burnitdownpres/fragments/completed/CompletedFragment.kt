package com.example.burnitdownpres.fragments.completed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.burnitdownpres.R
import com.example.burnitdownpres.database.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_completed.view.*

/**
 * CompletedFragment.kt
 * @author Jonathan Duran
 * @author Humberto Martinez
 * Description: Based on ListFragment.kt
 * UI Fragment to inflate fragment_completed.xml
 * Initializes RecyclerView to hold Completed Projects
 */


class CompletedFragment : Fragment() {

    private lateinit var mProjectViewModel: ProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_completed, container, false)


        //Recycler View
        val adapter = CompletedAdapter()
        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //ProjectViewModel
        mProjectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
        mProjectViewModel.readDoneProjects.observe(viewLifecycleOwner, Observer { project ->
            adapter.setData(project)
        })

        return view
    }


}