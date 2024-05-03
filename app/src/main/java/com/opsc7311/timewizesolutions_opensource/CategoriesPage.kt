package com.opsc7311.timewizesolutions_opensource

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.opsc7311.timewizesolutions_opensource.R

class CategoriesPage : AppCompatActivity() {

    private lateinit var projectNameTxt: EditText
    private lateinit var projectDescriptionTxt: EditText
    private lateinit var addBtn: Button
    private lateinit var backBtn: Button

    private val projectList = mutableListOf<Pair<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories_page)

        projectNameTxt = findViewById(R.id.projectNameTxt)
        projectDescriptionTxt = findViewById(R.id.projectDescriptionTxt)
        addBtn = findViewById(R.id.addbtn)
        backBtn = findViewById(R.id.backBtn6)

        addBtn.setOnClickListener {
            val projectName = projectNameTxt.text.toString()
            val projectDescription = projectDescriptionTxt.text.toString()

            if (projectName.isNotEmpty() && projectDescription.isNotEmpty()) {
                projectList.add(Pair(projectName, projectDescription))
                projectNameTxt.text.clear()
                projectDescriptionTxt.text.clear()
            }
        }

        backBtn.setOnClickListener {
            navigateToTimeMenu()
        }
    }

    private fun navigateToTimeMenu() {
        val intent = Intent(this, TimeSheetPG::class.java)
        startActivity(intent)
        finish()
    }
}
