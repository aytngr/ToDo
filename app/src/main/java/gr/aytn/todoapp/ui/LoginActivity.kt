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
import java.util.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userViewModel: UserViewModel by viewModels()

//        val correctEmail = Constants.EMAIL
//        val correctPassword = Constants.PASSWORD
//
        val etEmail: EditText = findViewById(R.id.email)
        val etPassword: EditText = findViewById(R.id.password)
        val tvMessage: TextView = findViewById(R.id.message)
        val btnLogin : Button = findViewById(R.id.login)
        val btnToRegister : TextView = findViewById(R.id.to_register)

        var user: User? = null

        btnToRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            userViewModel.findByEmail(email.trim()).observe(this, androidx.lifecycle.Observer {
                user = it
            })

            if (user!=null) {
                if (user!!.email.equals(email.trim()) &&
                    user!!.password.equals(password.trim())
                ) {
                    startActivity(Intent(this, MainActivity::class.java));
                }
            }

//            if(email == correctEmail && password == correctPassword){
//                /*
//                tvMessage.text = "Siz uğurla daxil oldunuz!"
//                tvMessage.background = ContextCompat.getDrawable(this,R.drawable.correct_credentials_message
//                 */
//                startActivity(Intent(this, SecondActivity::class.java))
//                finish()
//            }else{
//                if(email != correctEmail) {
//                    etEmail.error = "Email yanlışdır!"
//                }
//                if(password != correctPassword){
//                    etPassword.error = "Şifrə yanlışdır!"
//                }
//                tvMessage.text = "Email və ya şifrə yanlışdır!"
//                tvMessage.background = ContextCompat.getDrawable(this,
//                    R.drawable.wrong_credentials_message
//                )
//            }
        }
    }
}