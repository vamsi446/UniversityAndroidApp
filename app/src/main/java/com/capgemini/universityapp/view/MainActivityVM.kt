package com.capgemini.universityapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.universityapp.R
import com.capgemini.universityapp.model.Repository
import com.capgemini.universityapp.model.Student
import com.capgemini.universityapp.viewModels.StudentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityVM : AppCompatActivity() {


    lateinit var model: StudentViewModel
    lateinit var rView : RecyclerView
    var adapter: StudentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val vmProvider = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory(application))

        model = vmProvider.get(StudentViewModel::class.java)

        //DO NOT do this
        //model = StudentViewModel(application)



        setupRecyclerView()
        //updateList()

        //Log.d("MainActivity", "OnCreate List: $studentList")

        model.studentList.observe(this, Observer {
            val stdList = it
            Log.d("MainActivity", "Observer: $stdList")
            // setup adapter

            adapter = StudentAdapter(model.studentList.value!!)
            rView.adapter = adapter


        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Add Student")
        menu?.add("Update")
        menu?.add("Delete")
        menu?.add("Delete All")
        menu?.add("About Us")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            "About Us" -> {
                val abtIntent = Intent(this, AboutActivity::class.java)
                startActivity(abtIntent)
            }
            "Add Student" -> {
                //repository.addStudent(Student("Merry", "Smith", 90))
                val stdIntent = Intent(this, StudentActivity::class.java)
                startActivity(stdIntent)
            }
            "Update" -> {
                if(adapter?.selectedStd != null) {
                    val stdIntent = Intent(this, StudentActivity::class.java)
                    stdIntent.putExtra("isUpdate", true)
                    stdIntent.putExtra("fname", adapter?.selectedStd?.firstName)
                    stdIntent.putExtra("lname", adapter?.selectedStd?.lastName)
                    stdIntent.putExtra("marks", adapter?.selectedStd?.marks)
                    stdIntent.putExtra("id", adapter?.selectedStd?.id)
                    startActivity(stdIntent)
                }
                else {
                    Toast.makeText(this, "Please select student to Update",
                        Toast.LENGTH_LONG).show()
                }
            }
            "Delete" -> {
                if(adapter?.selectedStd != null) {
                   model.deleteStudent(adapter?.selectedStd!!)
                }
                else {
                    Toast.makeText(this, "Please select student to delete",
                        Toast.LENGTH_LONG).show()
                }
            }
            "Delete All" -> {
                model.deleteAll()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        model.getStudents()
     //   updateList()
    }

    fun setupRecyclerView(){
        rView = findViewById(R.id.rView)
        rView.layoutManager = LinearLayoutManager(this)

        //rView.adapter = StudentAdapter(studentList ?: mutableListOf())

    }

//    fun updateList(){
//        val stdList = model.studentList
//        if (adapter == null){
//
//             adapter = StudentAdapter(model.studentList)
//            rView.adapter = adapter
//        }
//        else {
//            adapter?.notifyDataSetChanged()
//        }
//        Log.d("MainActivity", "updateList(): $stdList")
//    }

}