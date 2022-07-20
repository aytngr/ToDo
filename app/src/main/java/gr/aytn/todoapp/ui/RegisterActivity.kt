package gr.aytn.todoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.todoapp.R
import gr.aytn.todoapp.model.User
import gr.aytn.todoapp.ui.viewmodel.UserViewModel

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val userViewModel: UserViewModel by viewModels()

        val etName: EditText = findViewById(R.id.name_register)
        val etEmail: EditText = findViewById(R.id.email_register)
        val etPassword: EditText = findViewById(R.id.password_register)
        val tvMessage: TextView = findViewById(R.id.message_register)
        val btnSignup : Button = findViewById(R.id.signup)
        val btnToLogin : TextView = findViewById(R.id.to_login)



        btnSignup.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (name!="" && email!="" && password!=""){
                userViewModel.addUser(User(name,email,password,""))
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            else {
                tvMessage.text = "Please fill all the fields!"
                tvMessage.background = ContextCompat.getDrawable(this,
                    R.drawable.wrong_credentials_message
                )
            }
        }
        btnToLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}