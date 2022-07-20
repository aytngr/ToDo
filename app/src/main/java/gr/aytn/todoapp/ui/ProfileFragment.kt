package gr.aytn.todoapp.ui

import android.content.Intent
import android.database.CursorWindow
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.todoapp.databinding.FragmentProfileBinding
import gr.aytn.todoapp.prefs
import gr.aytn.todoapp.ui.viewmodel.UserViewModel
import java.io.IOException
import java.lang.reflect.Field


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try {
            val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.setAccessible(true)
            field.set(null, 100 * 1024 * 1024) //the 100MB is the new size
        } catch (e: Exception) {
                e.printStackTrace()
        }



        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val image: ImageView = binding.image
        val btnSelectImage: Button = binding.btnEdit
        val name: TextView = binding.name

        name.text = prefs.name

//        image.setImageURI(Uri.parse(prefs.image))
        userViewModel.getUserById(prefs.user_id).observe(viewLifecycleOwner, Observer {
            try{
                // decode base64 string
                val bytes: ByteArray = Base64.decode(it.image, Base64.DEFAULT)
                // Initialize bitmap
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                // set bitmap on imageView
                image.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        })


        btnSelectImage.setOnClickListener {
            startActivity(Intent(activity!!,EditProfileActivity::class.java))

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}