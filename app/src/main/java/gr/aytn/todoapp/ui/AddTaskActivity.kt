package gr.aytn.todoapp.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.todoapp.R
import gr.aytn.todoapp.model.Task
import gr.aytn.todoapp.prefs
import java.util.*
import androidx.lifecycle.Observer
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity() {

    val myCalendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val taskViewModel: TaskViewModel by viewModels()
        val userViewModel: UserViewModel by viewModels()

        var addedTask: Task? = null

        val backBtn: ImageButton = findViewById(R.id.back_button)
        val createBtn: Button = findViewById(R.id.create_button)
        val etTitle: EditText = findViewById(R.id.et_title)
        val etDescription: EditText = findViewById(R.id.et_description)
        val etDeadline: EditText = findViewById(R.id.deadline)

        var date: String = ""
        var time: String = ""

        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        Log.i("tag","$month")
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val hour = myCalendar.get(Calendar.HOUR)
        val minute = myCalendar.get(Calendar.MINUTE)
        val MONTHS: ArrayList<String> = arrayListOf("January","February","March","April","May","June","July","August","September","October","November","December")

        var selectedDate:String = ""
        var selectedTime:String = ""

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("sharedpref", Context.MODE_PRIVATE)


        createBtn.setOnClickListener {
            if(date==""){
                date = "$day ${MONTHS[month]} $year"
            }
            if (etTitle.text.toString() != "" && etDeadline.text.toString() != ""){
                taskViewModel.addTask(Task(etTitle.text.toString(),etDescription.text.toString(),
                date,time, prefs.user_id))

//                taskViewModel.getLastCustomerId().observe(this, Observer{
//                    Log.i("add","${etTitle.text.toString()}")
//                    Log.i("add","$it")
//                    if(it != null){
//                        userViewModel.addUserTask(UserTask(prefs.user_id,it))
//                    }
//                })
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        etDeadline.setOnClickListener {

            val timeDialog =  TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{
                    _,selectedHour,selectedMinute ->

                time = "$selectedHour:$selectedMinute"
                etDeadline.setText("$time, $date")
            },hour,minute,true)
            timeDialog.show()

            val dateDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{
                    _,selectedYear,selectedMonth,selectedDay ->

                date = "$selectedDay ${MONTHS[selectedMonth]} $selectedYear"
                etDeadline.setText(date)
            },year,month,day)
            dateDialog.datePicker.minDate = System.currentTimeMillis() - 86400000
            dateDialog.show()


        }

        backBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Discard")
            builder.setMessage("Are you sure to discard?")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))
            builder.setPositiveButton("Discard") { dialog, which ->
                finish()
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }
    }
}