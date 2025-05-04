package com.example.student_menu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView


class studentAdapter(
    private val students: ArrayList<Student>,
    val context: Context
) : RecyclerView.Adapter<studentAdapter.StudentViewHolder>() {

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvMssv: TextView = view.findViewById(R.id.tvMssv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.tvName.text = student.name
        holder.tvMssv.text = student.mssv

        // Hiển thị PopupMenu khi nhấn giữ item
        holder.itemView.setOnLongClickListener { v ->
            val popup = PopupMenu(context, v)
            popup.inflate(R.menu.context_menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_update -> {
                        if (context is MainActivity) {
                            context.updateStudent(student, position)
                        }
                        true
                    }
                    R.id.menu_delete -> {
                        AlertDialog.Builder(context)
                            .setTitle("Xác nhận")
                            .setMessage("Bạn có chắc muốn xóa sinh viên này không?")
                            .setPositiveButton("Xóa") { _, _ ->
                                students.removeAt(position)
                                notifyItemRemoved(position)
                            }
                            .setNegativeButton("Hủy", null)
                            .show()
                        true
                    }
                    R.id.menu_call -> {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${student.phone}")
                        context.startActivity(intent)
                        true
                    }
                    R.id.menu_email -> {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:${student.email}")
                        }
                        context.startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

            popup.show()
            true
        }
    }
}



