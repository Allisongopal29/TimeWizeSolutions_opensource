package com.opsc7311.timewizesolutions_opensource

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class TimeSheetPG : AppCompatActivity() {

    private val projectList = mutableListOf<String>()
    private lateinit var sendForApprovalBtn: Button

    private lateinit var uploadbtn: Button
    private lateinit var pictureimageview: ImageView


    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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





        val addBtn = findViewById<Button>(R.id.addbtn)
        val projectEditText = findViewById<EditText>(R.id.projectedittext)
        val descriptionEditText = findViewById<EditText>(R.id.Descriptionedittext)
        val minDailyGoalEditText = findViewById<EditText>(R.id.minDailyGoalEdittxt)
        val maxDailyGoalEditText = findViewById<EditText>(R.id.maxDailyGoaledittxt)
        val pictureImageView = findViewById<ImageView>(R.id.pictureimageview)
        val editTextDate = findViewById<EditText>(R.id.editTextDate)
        //val resultstxtview = findViewById<TextView>(R.id.resultstxtview)




        addBtn.setOnClickListener {
            // Retrieve text from EditText fields
            val projectName = projectEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()
            val minDailyGoal = minDailyGoalEditText.text.toString().trim()
            val maxDailyGoal = maxDailyGoalEditText.text.toString().trim()
            val Date = editTextDate.text.toString().trim()
            val ttlHours = resultstxtview.text.toString().trim()

            //val imageUri = (pictureimageview.drawable as? BitmapDrawable)?.let { bitmapDrawable ->



          //  }


            val imageBitmap = (pictureimageview.drawable as? BitmapDrawable)?.bitmap

            // Check if the project name is not empty
            if (projectName.isNotEmpty()) {
                // Add project to the list
                projectList.add(Project(projectName, description, minDailyGoal, maxDailyGoal,Date,ttlHours, imageBitmap).toString())

                // Clear EditText fields after adding
                projectEditText.text.clear()
                descriptionEditText.text.clear()
                minDailyGoalEditText.text.clear()
                maxDailyGoalEditText.text.clear()
                editTextDate.text.clear()
                resultstxtview.text.toString()


                // Show a toast message indicating the project has been added
                showToast("Added to the list: $projectName")

                // Pass the project list data to ListViewTS activity
                val intent = Intent(this, ListViewTS::class.java)
                intent.putExtra("projectList", ArrayList(projectList))
                startActivity(intent)




            }
            // Pass the project list data to ListViewTS activity
            val intent = Intent(this, ListViewTS::class.java)
            intent.putExtra("projectList", ArrayList(projectList))
            startActivity(intent)
        }

    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"  // Set the MIME type to images only
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val chooserIntent = Intent.createChooser(pickIntent, "Select Image from")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        resultLauncher.launch(chooserIntent)
    }




    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

data class Project(
    val projectName: String,
    val description: String,
    val minDailyGoal: String,
    val maxDailyGoal: String,
    val date: String,
    val ttlHours: String,

    val imageBitmap: Bitmap? = null

) {
    override fun toString(): String {
        return "Project Name: $projectName\n" +
                "Description: $description\n" +
                "Min Daily Goal: $minDailyGoal\n" +
                "Max Daily Goal: $maxDailyGoal\n" +
                "Date: $date\n" +
                "Total Hours: $ttlHours"
    }
}
