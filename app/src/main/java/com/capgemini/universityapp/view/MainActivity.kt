package com.capgemini.universityapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.universityapp.R
import com.capgemini.universityapp.model.Repository
import com.capgemini.universityapp.model.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var repository: Repository
    var studentList =  mutableListOf<Student>()
    lateinit var rView : RecyclerView
    var adapter: StudentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = Repository(this)
        updateList()
        setupRecyclerView()

        //Log.d("MainActivity", "OnCreate List: $studentList")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Add Student")
        menu?.add("Update")
        menu?.add("Delete")
        menu?.add("Delete All")
        menu?.add("Refresh")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title){
            "Add Student" -> {
                //repository.addStudent(Student("Merry", "Smith", 90))
                val stdIntent = Intent(this, StudentActivity::class.java)
                startActivity(stdIntent)
            }
            "Update" -> {
                //repository.updateStudent(Student("John", "Smith", 75, 1))
            }
            "Delete" -> {}
            "Delete All" -> {
                repository.deleteAllStudents()
            }
            "Refresh" -> {
                updateList()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        updateList()
    }

    fun setupRecyclerView(){
        rView = findViewById(R.id.rView)
        rView.layoutManager = LinearLayoutManager(this)

        //rView.adapter = StudentAdapter(studentList ?: mutableListOf())

    }

    fun updateList(){
        studentList.clear()
        CoroutineScope(Dispatchers.Default).launch {

            studentList.addAll(repository.allStudents())

            CoroutineScope(Dispatchers.Main).launch {
                if(adapter == null){
                    adapter = StudentAdapter(studentList)
                    rView.adapter = adapter
                }else{
                    adapter?.notifyDataSetChanged()
                }

            }


            Log.d("MainActivity", "updateList(): $studentList")
        }

    }
}