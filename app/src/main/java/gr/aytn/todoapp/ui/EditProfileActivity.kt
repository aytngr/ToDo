package gr.aytn.todoapp.ui

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.CursorWindow
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import gr.aytn.todoapp.R
import gr.aytn.todoapp.prefs
import gr.aytn.todoapp.ui.viewmodel.UserViewModel
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.reflect.Field


@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {
    var image: ImageView? = null
    var current_name: EditText? = null
    var btnSave: Button? = null
    private var imageUri: Uri? = null

    private val userViewModel: UserViewModel by viewModels()

    private val CAMERA_PERMISSION_CODE = 100
    private val STORAGE_PERMISSION_CODE = 101

    var encodedImage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        try {
            val field: Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.setAccessible(true)
            field.set(null, 100 * 1024 * 1024) //the 100MB is the new size
        } catch (e: Exception) {
            e.printStackTrace()
        }
        image = findViewById(R.id.change_image)
        current_name = findViewById(R.id.et_current_name)
        btnSave = findViewById(R.id.btn_save)
        val btnChangeImage: Button = findViewById(R.id.btn_change)
        val mCurrentPhotoPath: String = ""

        current_name?.setText(prefs.name)

        userViewModel.getUserById(prefs.user_id).observe(this, Observer {
            // decode base64 string
            val bytes: ByteArray = Base64.decode(it.image, Base64.DEFAULT)
            // Initialize bitmap
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            // set bitmap on imageView
            image?.setImageBitmap(bitmap)
        })

        btnSave?.setOnClickListener {

            userViewModel.getUserById(prefs.user_id).observe(this, Observer{
                if(it != null){
                    userViewModel.updateUser(it, current_name?.text.toString())
                    prefs.name = current_name?.text.toString()
                    if(encodedImage != ""){
                        saveUserImage(encodedImage)
                        Log.i("edit profiel", "${it.image}")
                    }

                }
            })
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        btnChangeImage.setOnClickListener {

            if(checkAndRequestPermissions()){
                val builder = android.app.AlertDialog.Builder(this)
                    .create()
                val view = layoutInflater.inflate(R.layout.selecting_dialog, null)
                val galleryButton = view.findViewById<Button>(R.id.btn_gallery)
                val cameraButton = view.findViewById<Button>(R.id.btn_camera)
                builder.setView(view)
                galleryButton.setOnClickListener {
                    openGallery()
                    builder.dismiss()
                }
                cameraButton.setOnClickListener {
                    openCamera()
                    builder.dismiss()
                }
                builder.setCanceledOnTouchOutside(true)
                builder.show()
            }
        }
    }
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
    private fun checkAndRequestPermissions(): Boolean {

        val storagePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val cameraPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)

        val listPermissionsNeeded: MutableList<String> = ArrayList()

        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private val IMAGE_CAPTURE_CODE = 1001
    private fun openCamera() {
        val values = ContentValues()
        imageUri = this.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    private val GALLERY_REQUEST_CODE = 1006
    fun openGallery() {

        val values = ContentValues()
//        imageUri = this.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//        imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val gallery = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
//        gallery.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(gallery, GALLERY_REQUEST_CODE)
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE)
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 0, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
    fun saveUserImage(encodedImage: String){
        userViewModel.getUserById(prefs.user_id).observe(this, Observer {
            userViewModel.updateUserImage(it, encodedImage)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)// Callback from camera intent
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK){
            val imageStream: InputStream? = contentResolver.openInputStream(imageUri!!)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            encodedImage = encodeImage(selectedImage)
            image?.setImageURI(imageUri)

//            val s: String = imageUri.toString()
//            prefs.image = s
        }
        else if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data?.getData()!=null){
            imageUri = data.data
            val imageStream: InputStream? = contentResolver.openInputStream(imageUri!!)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            encodedImage = encodeImage(selectedImage)
            image?.setImageURI(imageUri)

//            val s: String = imageUri.toString()
//            prefs.image = s
        }
    }

}