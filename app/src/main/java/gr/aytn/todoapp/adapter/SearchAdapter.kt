package gr.aytn.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gr.aytn.todoapp.R
import gr.aytn.todoapp.model.Task

class SearchAdapter(private val mList: ArrayList<Task>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.task_search_result,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = mList[position]

        holder.tvTitle.text = task.title
        holder.tvDescription.text = task.description
        holder.tvTime.text = task.date
        holder.tvTitle.paint.isStrikeThruText = task.completed
    }
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        var tvTitle: TextView = itemView.findViewById(R.id.title)
        val tvDescription: TextView = itemView.findViewById(R.id.description)
        val tvTime: TextView = itemView.findViewById(R.id.time)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}