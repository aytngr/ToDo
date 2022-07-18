package gr.aytn.todoapp.ui

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {
    var image: ImageView? = null
    var current_name: EditText? = null
    var btnSave: Button? = null
    lateinit var bmOptions: BitmapFactory.Options
    lateinit var currentPhotoPath: String
    val PERMISSION_REQUEST_CODE  = 123
    val CAMERA_REQUEST_CODE = 102
    val REQUEST_CAMERA = 0
    private var imageUri: Uri? = null

    private val userViewModel: UserViewModel by viewModels()

    private val CAMERA_PERMISSION_CODE = 100
    private val STORAGE_PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)


        image = findViewById(R.id.change_image)
        current_name = findViewById(R.id.et_current_name)
        btnSave = findViewById(R.id.btn_save)
        val btnChangeImage: Button = findViewById(R.id.btn_change)
        val mCurrentPhotoPath: String = ""

        current_name?.setText(prefs.name)
        image?.setImageURI(Uri.parse(prefs.image))

        btnSave?.setOnClickListener {
            userViewModel.getUserById(prefs.user_id).observe(this, Observer{
                if(it != null){
                    userViewModel.updateUser(it, current_name?.text.toString())
                    prefs.name = current_name?.text.toString()
                }
            })
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        btnChangeImage.setOnClickListener {
//            val storagePermission = checkPermission(
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                STORAGE_PERMISSION_CODE)
//
//            val cameraPermission = checkPermission(Manifest.permission.CAMERA,
//                CAMERA_PERMISSION_CODE)

            if(checkAndRequestPermissions()){
                openCameraInterface()
            }

        }

    }
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
    private fun checkAndRequestPermissions(): Boolean {

        val storagePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
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
    // Function to check and request permission.
    private fun checkPermission(permission: String, requestCode: Int) : Boolean{
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            return false
        }
        return true
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.
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
//
//
//    val CAMERA_PERMISSION_CODE = 1000;
//    private fun requestCameraPermission(): Boolean {
//        var permissionGranted = false// If system os is Marshmallow or Above, we need to request runtime permission
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            val cameraPermissionNotGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
//            if (cameraPermissionNotGranted){
//                val permission = arrayOf(Manifest.permission.CAMERA)  // Display permission dialog
//                requestPermissions(permission, CAMERA_PERMISSION_CODE)
//            }
//            else{
//                // Permission already granted
//                permissionGranted = true
//            }
//        }
//        else{
//            // Android version earlier than M -&gt; no need to request permission
//            permissionGranted = true
//        }
//        return permissionGranted
//    }
//    private val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
//    fun requestWrite(): Boolean{
//        var permissionGranted2 = false
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            )
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
//            )
//        } else {
//            permissionGranted2 = true
//        }
//        return permissionGranted2
//    }
//
////    private fun requestPermission() {
////        ActivityCompat.requestPermissions(
////            this, arrayOf(Manifest.permission.CAMERA),
////            PERMISSION_REQUEST_CODE
////        )
////    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        Log.i("edir profile","$grantResults")
//        when (requestCode) {
//            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
//
//            }
//            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> if (grantResults.size > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
//                openCameraInterface()
//            }else {
//                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                    != PackageManager.PERMISSION_GRANTED
//                ) {
//                    requestCameraPermission()
//                    requestWrite()
//                }
//
//            }
//        }
//    //        if (requestCode == CAMERA_PERMISSION_CODE) {
////            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
////                // Permission was granted
////                openCameraInterface()
////            }
////            else{
////                // Permission was denied
////                showAlert("Camera permission was denied. Unable to take a picture.")
////            }
////        }
//    }
//    private fun showAlert(message: String) {
//        val builder = AlertDialog.Builder(this)
//        builder.setMessage(message)
//        builder.setPositiveButton("Ok", null)
//        val dialog = builder.create()
//        dialog.show()
//    }
    private val IMAGE_CAPTURE_CODE = 1001
    private fun openCameraInterface() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Take Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Take Picture")
        imageUri = this.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)// Create camera intent
        Log.i("edit proile", "${imageUri}")
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val s: String = imageUri.toString()
        prefs.image = s
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)// Launch intent
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)// Callback from camera intent
        if (resultCode == RESULT_OK){
            // Set image captured to image view
            image?.setImageURI(imageUri)
        }
    }
//
//
//    //    override fun onRequestPermissionsResult(
////    private fun cameraIntent() {
////        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
////        startActivityForResult(intent, REQUEST_CAMERA)
////    }
////
//
//    private fun checkPermission(): Boolean {
//        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Permission is not granted
//            false
//        } else true
//    }
////        requestCode: Int,
////        permissions: Array<out String>,
////        grantResults: IntArray
////    ) {
////        when (requestCode) {
////            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
////                cameraIntent()
////            } else {
////                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
////                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
////                    != PackageManager.PERMISSION_GRANTED
////                ) {
////                    requestPermission()
////                }
////
////            }
////        }
////    }
////
////
////    private fun setPic() {
////        // Get the dimensions of the View
////        val targetW: Int = image?.width!!
////        val targetH: Int = image?.height!!
////
////        val bmOptions = BitmapFactory.Options().apply {
////            // Get the dimensions of the bitmap
////            inJustDecodeBounds = true
////
////            BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
////
////            val photoW: Int = outWidth
////            val photoH: Int = outHeight
////
////            // Determine how much to scale down the image
////            val scaleFactor: Int = Math.max(1, Math.min(photoW / targetW, photoH / targetH))
////
////            // Decode the image file into a Bitmap sized to fill the View
////            inJustDecodeBounds = false
////            inSampleSize = scaleFactor
////            inPurgeable = true
////        }
////        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
////            image?.setImageBitmap(bitmap)
////        }
////    }
////
////    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
////        super.onActivityResult(requestCode, resultCode, data)
////        val thumbnail  = data?.extras!!["data"] as Bitmap?
////        createImageFile()
////        Log.i("Edit Profile", "$currentPhotoPath")
////        image?.setImageBitmap(thumbnail)
////    }
////
//    @Throws(IOException::class)
//    private fun createImageFile(): File {
//        // Create an image file name
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        return File.createTempFile(
//            "JPEG_${timeStamp}_", /* prefix */
//            ".jpg", /* suffix */
//            storageDir /* directory */
//        ).apply {
//            // Save a file: path for use with ACTION_VIEW intents
//            currentPhotoPath = absolutePath
//        }
//    }

}