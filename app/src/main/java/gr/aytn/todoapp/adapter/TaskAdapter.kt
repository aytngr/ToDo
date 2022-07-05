package gr.aytn.todoapp.adapter

import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gr.aytn.todoapp.R
import gr.aytn.todoapp.model.Task


class TaskAdapter(private val mList: ArrayList<Task>, private val listener: OnItemClickListener?):RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private lateinit var view: View
    private var item: Task? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.task, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = mList[position]

        holder.bind(task)
        holder.itemView.isLongClickable = true

        holder.checkBox.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                listener?.onCheckBoxClick(task, holder.checkBox.isChecked)
            }
        }
        holder.itemView.setOnLongClickListener{
            Log.i("adapter","$task")
            setTask(task)
            return@setOnLongClickListener false
        }
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        var tvTitle: TextView = itemView.findViewById(R.id.title)
        val tvDescription: TextView = itemView.findViewById(R.id.description)
        val tvTime: TextView = itemView.findViewById(R.id.time)
        val checkBox: CheckBox = itemView.findViewById(R.id.check_completed)

        fun bind(task: Task) {
            tvTitle.text = task.title
            tvDescription.text = task.description
            tvTime.text = task.date
            checkBox.isChecked = task.completed
            tvTitle.paint.isStrikeThruText = task.completed
        }
    }
    interface OnItemClickListener {
        fun onCheckBoxClick(task: Task, isChecked: Boolean)
    }

    fun getTask(): Task?{
        return item
    }
    fun setTask(task: Task){
        item = task
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}