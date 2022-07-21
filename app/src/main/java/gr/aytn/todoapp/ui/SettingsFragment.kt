package gr.aytn.todoapp.ui

import android.app.SearchManager
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.zxing.integration.android.IntentIntegrator
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
        val btnScan = binding.scan

        btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(activity!!)
            builder.setTitle("Logout")
            builder.setMessage("Are you sure to logout?")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))
            builder.setPositiveButton("Logout") { dialog, which ->
                startActivity(Intent(activity!!, LoginActivity::class.java))
                prefs.token = ""
                prefs.user_id = -1
                prefs.image = "android.resource://gr.aytn.todoapp/drawable/user_default"
                activity!!.finish()
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }

        btnScan.setOnClickListener {
            val intentIntegrator = IntentIntegrator.forSupportFragment(this)
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.setOrientationLocked(false)
            intentIntegrator.initiateScan()
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var result = IntentIntegrator.parseActivityResult(resultCode,data)
        if(result != null){
            Log.i("settings","$result is not null")
            AlertDialog.Builder(context!!)
                .setMessage("Would you like to go to ${result.contents}?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener{
                        _, _ ->
                    val intent = Intent(Intent.ACTION_WEB_SEARCH)
                    intent.putExtra(SearchManager.QUERY,result.contents)
                    startActivity(intent)
                })
                .setNegativeButton("No",DialogInterface.OnClickListener { _, _ ->  })
                .show()
        }else{
            Log.i("settings","$result is null")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}