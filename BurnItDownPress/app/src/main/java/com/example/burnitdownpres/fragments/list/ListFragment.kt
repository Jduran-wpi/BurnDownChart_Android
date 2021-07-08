package com.example.burnitdownpres.fragments.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.burnitdownpres.R
import com.example.burnitdownpres.database.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

/**
 * ListFragment.kt
 * @author Humberto Martinez
 * Description: Ui Fragment to initialize all Buttons and fill RecyclerView
 * with Projects using the ListAdapter. Uses appropriate ViewModel to fetch
 * project data. Initializes menu to go to AboutFragment and Completed Fragment
 */

class ListFragment : Fragment() {


    private lateinit var mProjectViewModel: ProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list, container, false)

        //Overflow Menu
        setHasOptionsMenu(true)

        //Recycler View
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //ProjectViewModel
        mProjectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
        mProjectViewModel.readAllProjects.observe(viewLifecycleOwner, Observer { project ->
            adapter.setData(project)
        })

        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_meni,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
        view!!.findNavController()) || super.onOptionsItemSelected(item)
    }


}