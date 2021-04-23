package com.capgemini.universityapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capgemini.universityapp.model.RepositoryVM
import com.capgemini.universityapp.model.University
import kotlinx.coroutines.launch

class UniViewModel(app: Application) : AndroidViewModel(app){

    var univ = University("IIT Roorkee",
        "Roorkee, Uttarakhand", "contact@iitr.in")


    var studentCount = MutableLiveData<Int>()

    private val repo = RepositoryVM(app)

    init {
        updateCount()
    }

    fun updateCount(){
        // get count from db
        viewModelScope.launch {
            val list = repo.allStudents()
            studentCount.postValue(list.size)
        }
    }
}