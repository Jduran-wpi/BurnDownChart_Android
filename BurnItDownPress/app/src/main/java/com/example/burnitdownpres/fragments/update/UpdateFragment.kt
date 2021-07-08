package com.example.burnitdownpres.fragments.update

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.burnitdownpres.R
import com.example.burnitdownpres.database.Project
import com.example.burnitdownpres.database.ProjectViewModel
import com.example.burnitdownpres.database.TaskViewModel
import com.example.burnitdownpres.database.TaskViewModelFactory
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.view.LineChartView

/**
 * UpdateFragment.kt
 * @author Humberto Martinez
 * @author Jonathan Duran
 * Description: UI Fragment to initialize UI components
 * Displays Unique List of Tasks per each Project
 * Displays Charts to represent Project status
 */


class UpdateFragment : Fragment() {


    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mProjectViewModel: ProjectViewModel
    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var mTaskViewModelFactory: TaskViewModelFactory

    private var currentDay = 0


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(savedInstanceState != null){
            this.currentDay = savedInstanceState.getInt("KEY_CUR_DAY")
        }
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        if(savedInstanceState != null){
            this.currentDay = savedInstanceState.getInt("KEY_CUR_DAY")
        }

        //Recycler View
        val adapter = UpdateAdapter()
        val recyclerView = view.task_recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        mProjectViewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)

        mTaskViewModelFactory = TaskViewModelFactory(mProjectViewModel.projectDatabaseDao, args.currentUser.project.projectID)
        mTaskViewModel = ViewModelProvider(this, mTaskViewModelFactory).get(TaskViewModel::class.java)

        mTaskViewModel.readAllTasks.observe(viewLifecycleOwner, Observer { task ->
            adapter.setData(task)

        })




        view.updateProjName.setText(args.currentUser.project.title)
        view.updateProjLength.setText(args.currentUser.project.daysToComplete.toString())

        view.update_btn.setOnClickListener{
            updateItem()
        }
        view.floatingActionButton2.setOnClickListener{
            finishTask()
        }
        view.floatingActionButton3.setOnClickListener{
            val action = UpdateFragmentDirections.actionUpdateFragmentToAddTaskFragment(args.currentUser.project)
            findNavController().navigate(action)
        }

        val textView: TextView = view.findViewById(R.id.dayCount)
        textView.text = currentDay.toString()

        view.Simbutton.setOnClickListener{

            if(currentDay < args.currentUser.project.daysToComplete!!) {
                this.currentDay += 1
                textView.text = currentDay.toString()
                createChart(view)
                createlineChart(view)
            }
        }

        //Method call to create graph
        createChart(view)


        //Method call to create line graph
        createlineChart(view)

        return view
    }

    private fun updateItem(){
        val projName = updateProjName.text.toString()
        val projLength = Integer.parseInt(updateProjLength.text.toString())

        if(inputCheck(projName, updateProjLength.text)){
            //Create Project Object
            val updatedProject = Project(args.currentUser.project.projectID, projName, projLength)
            //Update Current Project
            mProjectViewModel.update(updatedProject)
            Toast.makeText(requireContext(), "Update Successful", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(requireContext(), "Please Update Project", Toast.LENGTH_SHORT).show()
        }
    }
    private fun finishTask(){
        val updatedProject = Project(args.currentUser.project.projectID,
                args.currentUser.project.title,
                args.currentUser.project.daysToComplete,
                args.currentUser.project.daysSinceStart,
                args.currentUser.project.numberOfTasksRemaining,
                true)
        mProjectViewModel.update(updatedProject)
        Toast.makeText(requireContext(), "Project Completed!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_updateFragment_to_listFragment)
    }

    private fun inputCheck(projName: String, projLength: Editable): Boolean{
        return !(TextUtils.isEmpty(projName) || projLength.isEmpty())
    }



    //Method to create line chart
    private fun createlineChart(view: View){

        val lineChart: LineChartView = view.findViewById(R.id.linechart) as LineChartView

        //Grabs day value for current project
        val days = args.currentUser.project.daysToComplete

        //How many days including 0
        val totalDays = days!! + 1

        //Code for line 1
        //------------------------------------------------------------------------------------------
        //X-axis data
        val axisData: Array<String?> = arrayOfNulls(totalDays!!)

        //Y-axis data for timeline
        val yAxisData: Array<Double?> = arrayOfNulls(totalDays!!)

        //Stating value is left most point on graph
        //Assign size of task list
        var startValue = args.currentUser.tasks.size *1.0

        //Calculate what to subtract to get to zero
        val subtract: Double = startValue / days * 1.0

        //Assign point data
        for (i in 0 until totalDays){

                yAxisData[i] = startValue

            startValue -= subtract
        }


        //Where y-axis values will be stored
        val yAxisValues: ArrayList<PointValue> = arrayListOf<PointValue>()

        //Where x-axis values will be stored
        val axisValues : ArrayList<AxisValue> = arrayListOf<AxisValue>()

        //Loop to assign labels in Array list
        for(i in 0 until totalDays) {

            axisData[i] = "Day ${i}"
        }


        //Transfer values for X-axis
        for (i in 0 until axisData.size) {
            axisValues.add(i, AxisValue(i.toFloat()).setLabel(axisData[i]))
        }

        //Transfer values for y-axis
        for (i in 0 until yAxisData.size) {
            yAxisValues.add(PointValue(i.toFloat(), yAxisData[i]!!.toFloat()))
        }

        //create line for timeline
        val line: Line = Line(yAxisValues)

        //------------------------------------------------------------------------------

        //*******************************************************************************
        //Code for line 2
        //Y-axis data for line 2
        val yAxisData2: Array<Double?> = arrayOfNulls(totalDays!!)

        //Count for completed tasks
        var curTasksDone: Array<Double?> = arrayOfNulls(totalDays!!)

        for(i in 0 until curTasksDone.size){
            curTasksDone[i] = 0.0
        }

        var startValue2 = args.currentUser.tasks.size *1.0

        //Get completed tasks and assign tasks to day it was completed
        for(i in 0 until  args.currentUser.tasks.size){
            var task = args.currentUser.tasks.get(i)

            //Check if task is completed then increment it to day it was completed
            if(task.isComplete == true){

                var currentTask = curTasksDone[task.dayCompleted!!]

                currentTask = currentTask?.plus(1.0)

                curTasksDone[task.dayCompleted!!]= currentTask
            }
        }

        //Assign point data
        for (i: Int in 0 until currentDay+1){

                yAxisData2[i] = startValue2

                startValue2 = startValue2.minus(curTasksDone[currentDay]!!)
                if(startValue2 < 0.0){
                    startValue2 = 0.0
                }

        }


        //Where y-axis values will be stored for line 2
        val yAxisValues2: ArrayList<PointValue> = arrayListOf<PointValue>()

        //Transfer values for y-axis
        for (i in 0 until currentDay+1) {
            yAxisValues2.add(PointValue(i.toFloat(), yAxisData2[i]!!.toFloat()))
        }

        //create line for line2
        val line2: Line = Line(yAxisValues2).setColor(R.color.purple_500)


        //-------------------------------------------------------------------------------

        //Arraylist of line on chart
        val lines: MutableList<Line> = ArrayList()
        lines.add(line)
        lines.add(line2)


        //Getting chart data and adding list of line to it
        val data = LineChartData()
        data.lines = lines

        //Adding data to linechart
        lineChart.lineChartData = data

        //Adding x-axis values to show up
        val axis = Axis()
        axis.values = axisValues
        data.axisXBottom = axis
        axis.name = "Days To Complete"
        axis.setTextColor(R.color.black)


        //Adding values for y-axis to show up
        val yAxis = Axis()
        yAxis.setName("Tasks Completed");
        yAxis.textColor = (R.color.black)
        data.axisYLeft = yAxis


        val viewport = Viewport(lineChart.getMaximumViewport())
        //viewport.right = (days + 0.).toFloat()
        lineChart.setMaximumViewport(viewport)
        lineChart.setCurrentViewport(viewport)
    }

    //Method to create graph
    private fun createChart(view: View){
        //Bar chart code
        val barChart = view.findViewById(R.id.barchart) as BarChart

        //Grabs day value for current project
        val days = args.currentUser.project.daysToComplete

        //Creates label for bars in graph
        val labels = ArrayList<String>()

        //Loop to assign labels in Array list
        for(i in 0 until days!!) {
            labels.add("Day ${i + 1}")
        }

        //Array list for number bar will show
        val entries: ArrayList<BarEntry> = ArrayList()

        //Count for completed tasks
        var curTasksDone: Array<Float?> = arrayOfNulls<Float>(days!!)

        for(i in 0 until curTasksDone.size){
            curTasksDone[i] = 0.0F
        }

        //Get completed tasks and assign tasks to day it was completed
        for(i in 0 until  args.currentUser.tasks.size){
            var task = args.currentUser.tasks.get(i)

            //Check if task is completed then increment it to day it was completed
            if(task.isComplete == true){
                curTasksDone[task.dayCompleted!! - 1] = curTasksDone[task.dayCompleted!! - 1]?.plus(1.0F)
            }
        }

        //Loop to add entry to bar column(Day)
        for(i in 0 until  (currentDay)) {
            entries.add(BarEntry(curTasksDone[i]!!, i))
        }

        val bardataset = BarDataSet(entries, "Cells")

        val data = BarData(labels, bardataset)
        barChart.data = data // set the data and list of labels into chart

        barChart.setDescription("Project 1 BarChart") // set the description

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS)
        barChart.animateY(5000)
    }

    //Life cycle methods
    override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        state.putInt("KEY_CUR_DAY", this.currentDay)
    }


}