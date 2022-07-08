package gr.aytn.todoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.todoapp.R
import gr.aytn.todoapp.ui.viewmodel.TaskViewModel

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val taskViewModel: TaskViewModel by viewModels()

        val tvTitle: TextView = findViewById(R.id.title_details)
        val tvDate: TextView = findViewById(R.id.date_details)
        val tvDescription: TextView = findViewById(R.id.description_details)
        val checkbox: CheckBox = findViewById(R.id.checkbox_details)
        val tvInfo: TextView = findViewById(R.id.info_text)

        val btnBack: ImageButton = findViewById(R.id.back_button_details)

        val title = intent.getStringExtra("title")
        tvTitle.text = title
        tvDate.text = intent.getStringExtra("date")
        tvDescription.text = intent.getStringExtra("description")
        checkbox.isChecked = intent.getBooleanExtra("state", false)
        if(checkbox.isChecked){
            tvInfo.text = "Completed"
        }else{
            tvInfo.text = "Mark as Completed"
        }
        checkbox.setOnClickListener {
            if(checkbox.isChecked){
                tvInfo.text = "Completed"
                taskViewModel.searchByTitleResults(title!!).observe(this, Observer {
                    taskViewModel.onTaskCheckedChanged(it,true)
                })

            }else{
                tvInfo.text = "Mark as Completed"
                taskViewModel.searchByTitleResults(title!!).observe(this, Observer {
                    taskViewModel.onTaskCheckedChanged(it,false)
                })
            }
        }


        btnBack.setOnClickListener {
            finish()

        }
    }
}