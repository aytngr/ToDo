package gr.aytn.todoapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.todoapp.R
import gr.aytn.todoapp.adapter.TaskAdapter
import gr.aytn.todoapp.databinding.FragmentHomeBinding
import gr.aytn.todoapp.model.Task
import android.view.Menu
import android.view.MenuItem
import android.view.MenuInflater
import android.widget.ImageButton
import androidx.appcompat.widget.PopupMenu
import gr.aytn.todoapp.prefs
import gr.aytn.todoapp.ui.viewmodel.TaskViewModel
import gr.aytn.todoapp.ui.viewmodel.UserViewModel

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnCreateContextMenuListener,
    TaskAdapter.OnItemClickListener,PopupMenu.OnMenuItemClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: TaskViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val homeNavView: BottomNavigationView = binding.homeNavView
        val tvTotalTasks: TextView = binding.totalTasks
        val tvCompletedTasks: TextView = binding.completedTasks
        val btnMenu: ImageButton = binding.menuButton
        val tvUserName = binding.userName

//        var currentUserTasks : ArrayList<UserTask> = arrayListOf()
        var taskIdsOfCurrentUser : ArrayList<Int> = arrayListOf()
        var tasksOfCurrentUser : ArrayList<Task> = arrayListOf()

        taskViewModel.userTasksCount(prefs.user_id).observe(viewLifecycleOwner, Observer{
            tvTotalTasks.text = it.toString()
        })
        taskViewModel.userCompletedTasksCount(prefs.user_id).observe(viewLifecycleOwner, Observer{
            tvCompletedTasks.text = it.toString()
        })
        tvUserName.text = "Hi, ${prefs.name.substringBefore(" ", prefs.name)}"


        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(activity)

        binding.apply { registerForContextMenu(recyclerView) }
        registerForContextMenu(recyclerView)

        taskViewModel.getUserTodaysTasks(prefs.user_id).observe(viewLifecycleOwner, Observer{
            val adapter = TaskAdapter(it as ArrayList<Task>,this)
            recyclerView.adapter = adapter
        })

        homeNavView.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.today -> {
                    taskViewModel.getUserTodaysTasks(prefs.user_id).observe(viewLifecycleOwner, Observer{
                        val adapter = TaskAdapter(it as ArrayList<Task>,this)
                        recyclerView.adapter = adapter
                    })
                }
                R.id.all -> {

                    taskViewModel.getUserTasks(prefs.user_id).observe(viewLifecycleOwner, Observer {
                        val adapter = TaskAdapter(it as ArrayList<Task>, this)
                        recyclerView.adapter = adapter
                    })
                }
            }
            true
        }

        btnMenu.setOnClickListener {
            val popup = PopupMenu(activity!!, it)
            val menuInflater: MenuInflater = popup.menuInflater
            menuInflater.inflate(R.menu.home_up_menu, popup.menu)
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener() { item ->
                when (item.itemId) {
                    R.id.delete_completed -> {
                        taskViewModel.deleteAllCompleted(prefs.user_id)
                    }
                }
                true
            })
            popup.show()
        }
        return root
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_completed -> {
                taskViewModel.deleteAllCompleted(prefs.user_id)
                true
            }
            else -> false
        }
    }


    override fun onCheckBoxClick(task: Task, isChecked: Boolean) {
        taskViewModel.onTaskCheckedChanged(task, isChecked)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onTaskClick(task: Task) {
        val intent = Intent(activity, DetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu.add(
            Menu.NONE, R.id.delete,
            Menu.NONE, "Delete")

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var task: Task?
        task = (recyclerView.adapter as TaskAdapter).getTask()
        Log.i("home","$task")
        try {
            task = (recyclerView.adapter as TaskAdapter).getTask()
            Log.i("home","$task")
        } catch (e: Exception) {
            Log.i("home","catch")
            return super.onContextItemSelected(item)
        }
        when (item.itemId) {
            R.id.delete -> {
                if (task != null) {
                    taskViewModel.deleteTask(task)
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        return super.onContextItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}