package gr.aytn.todoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.todoapp.R
import gr.aytn.todoapp.adapter.SearchAdapter
import gr.aytn.todoapp.databinding.FragmentHomeBinding
import gr.aytn.todoapp.databinding.FragmentSearchBinding
import gr.aytn.todoapp.model.Task
import gr.aytn.todoapp.prefs

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.searchRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val btnSearch = binding.btnSearch
        val etSearch = binding.etTitle

        taskViewModel.getUserTasks(prefs.user_id).observe(viewLifecycleOwner, Observer{
            val adapter = SearchAdapter(it as ArrayList<Task>)
            recyclerView.adapter = adapter
        })

        btnSearch.setOnClickListener {
            if (etSearch.text.toString() != ""){
                taskViewModel.searchByTitleInsensitive(prefs.user_id, etSearch.text.toString()).observe(viewLifecycleOwner, Observer{
                    val adapter = SearchAdapter(it as ArrayList<Task>)
                    recyclerView.adapter = adapter
                })
            }

        }

        return root
    }
}