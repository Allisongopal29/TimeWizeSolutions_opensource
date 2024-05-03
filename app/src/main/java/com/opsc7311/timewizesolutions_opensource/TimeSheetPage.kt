package com.opsc7311.timewizesolutions_opensource

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.renderscript.ScriptGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class TimeSheetPage : AppCompatActivity() {

    private lateinit var ActivityMainBinding: ScriptGroup.Binding

    private lateinit var date: String
    private lateinit var startTime: String
    private lateinit var endTime: String
    private lateinit var totalHours: String
    private lateinit var minDailyGoal: String
    private lateinit var maxDailyGoal: String
    private lateinit var project: String
    private lateinit var task: String
    private lateinit var description: String
    private lateinit var photo: String

    private lateinit var uploadbtn: Button
    private lateinit var pictureimageview: ImageView

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            if (data != null) {
                val imageUri = data.data
                if (imageUri != null) {
                    // Image picked from gallery
                    pictureimageview.setImageURI(imageUri)
                } else {
                    // Image captured from camera
                    val imageBitmap = data.extras?.get("data") as Bitmap?
                    pictureimageview.setImageBitmap(imageBitmap)
                }
            }
        } else {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show()
        }
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_sheet_pg)

        uploadbtn = findViewById(R.id.uploadbtn);
        pictureimageview = findViewById(R.id.pictureimageview);




        val startimetextview = findViewById<EditText>(R.id.startimetextview)
        val endtimeedittext = findViewById<EditText>(R.id.endtimeedittext)
        val totalhrsbtn = findViewById<Button>(R.id.totalhrsbtn)
        val resultstxtview = findViewById<TextView>(R.id.resultstxtview)


        totalhrsbtn.setOnClickListener {

            val num1 = startimetextview.text.toString().toIntOrNull() ?: 0
            val num2 = endtimeedittext.text.toString().toIntOrNull() ?: 0


            val difference = num2 - num1


            resultstxtview.text = "Total Hours Worked: $difference"
        }

        // Set OnClickListener on the upload button to pick an image
        uploadbtn.setOnClickListener {
            pickImage()
        }


    }


    //method to upload image
    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"  // Set the MIME type to images only
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val chooserIntent = Intent.createChooser(pickIntent, "Select Image from")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        resultLauncher.launch(chooserIntent)

        //List of timesheet


    }


    }
