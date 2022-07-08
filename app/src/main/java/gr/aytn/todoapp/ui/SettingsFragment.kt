package gr.aytn.todoapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import gr.aytn.todoapp.R
import gr.aytn.todoapp.databinding.FragmentHomeBinding
import gr.aytn.todoapp.databinding.FragmentSettingsBinding
import gr.aytn.todoapp.prefs

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnLogout = binding.logout
        btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure to logout?")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))
            builder.setPositiveButton("Logout") { dialog, which ->
                startActivity(Intent(activity!!, LoginActivity::class.java))
                prefs.token = ""
                prefs.user_id = -1
                activity!!.finish()
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}