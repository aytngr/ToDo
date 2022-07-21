package gr.aytn.todoapp.ui

import android.app.SearchManager
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.messaging.FirebaseMessaging
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.todoapp.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var currentFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        bottomNavView.background = null
        bottomNavView.menu.getItem(2).isEnabled = false

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("main", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("main", "$token")
        })

        bottomNavView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.home -> {
                    currentFragment = HomeFragment()
                }
                R.id.search -> {
                    currentFragment = SearchFragment()
                }
                R.id.settings -> {
                    currentFragment = SettingsFragment()
                }
                R.id.person -> {
                    currentFragment = ProfileFragment()
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main,currentFragment).commit()
            true
        }

        fab.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}