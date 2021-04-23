package com.capgemini.universityapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.universityapp.R
import com.capgemini.universityapp.model.Student


class StudentAdapter(val data: List<Student>) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    private var selectedPos = -1
    var selectedStd : Student? = null

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val cBox = view.findViewById<CheckBox>(R.id.checkBox)
        val fNameT = view.findViewById<TextView>(R.id.fNameT)
        val lastT = view.findViewById<TextView>(R.id.lastNameT)
        val marksT = view.findViewById<TextView>(R.id.marksT)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.ViewHolder {

      val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_student_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentAdapter.ViewHolder, position: Int) {
        val std = data[position]
        holder.fNameT.text = std.firstName
        holder.lastT.text = std.lastName
        holder.marksT.text = "${std.marks}"

        holder.cBox.isChecked = (selectedPos == position)
        holder.cBox.setOnCheckedChangeListener { compoundButton, ischecked ->
            if (ischecked){
                selectedPos = holder.adapterPosition
                selectedStd = std
                notifyDataSetChanged()
            }
            else{
                selectedPos = -1
                selectedStd = null
                notifyDataSetChanged()
            }

        }



    }

    override fun getItemCount(): Int {
       return data.size
    }
}