package com.capgemini.universityapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.capgemini.universityapp.R

import com.capgemini.universityapp.model.Repository
import com.capgemini.universityapp.model.Student
import com.capgemini.universityapp.model.StudentDatabase
import com.capgemini.universityapp.viewModels.StudentViewModel
import kotlinx.android.synthetic.main.activity_student.*

class StudentActivity : AppCompatActivity() {

   // lateinit var repository: Repository

    lateinit var model: StudentViewModel
    var studentId: Int = 0
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        val vmProvider = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory(application))

        model = vmProvider.get(StudentViewModel::class.java)

        if (intent.hasExtra("isUpdate")){
            isUpdate = true
            fNameE.setText(intent.getStringExtra("fname"))
            lastNameE.setText(intent.getStringExtra("lname"))
            marksE.setText(intent.getIntExtra("marks", 0).toString())
            studentId = intent.getIntExtra("id",0)
        }

    }

    fun submitClick(view: View) {
        val fName = fNameE.text.toString()
        val lName = lastNameE.text.toString()
        val marks = marksE.text.toString()

      if (isUpdate){
          model.updateStudent(Student(fName, lName, marks.toInt(), studentId))
          Toast.makeText(
              this, "Student Updated",
              Toast.LENGTH_LONG
          ).show()

      }else {
          model.addStudent(Student(fName, lName, marks.toInt()))

          Toast.makeText(
              this, "Student Added",
              Toast.LENGTH_LONG
          ).show()
      }

        finish()

    }
}