package com.example.student_menu

import android.app.Activity
import android.content.Intent
import android.icu.text.Transliterator.Position
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var studentList = ArrayList<Student>()
    private lateinit var adapter: studentAdapter
    companion object {
        const val REQUEST_CODE_ADD = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        studentList.add(Student("Nguyen Manh Cuong", "20225268", "dddsd", "0987789813"))
        studentList.add(Student("Nguyen Manh Cuong", "20225268", "dddsd", "0987789813"))
        studentList.add(Student("Nguyen Manh Cuong", "20225268", "dddsd", "0987789813"))

        adapter = studentAdapter(studentList, this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_ADD)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD && resultCode == Activity.RESULT_OK) {
            val student = data?.getSerializableExtra("student") as? Student
            val position = data?.getIntExtra("position", -1) ?: -1
            student?.let {
                if (position != -1) {
                    // Cập nhật student cũ
                    studentList[position] = it
                    adapter.notifyItemChanged(position)
                } else {
                    // Thêm mới
                    studentList.add(it)
                    adapter.notifyItemInserted(studentList.size - 1)
                }
            }
        }
    }


    fun updateStudent(student: Student, position: Int) {
        val intent = Intent(this, AddStudentActivity::class.java)
        intent.putExtra("student", student)
        intent.putExtra("position", position)
        startActivityForResult(intent, REQUEST_CODE_ADD)
    }
}
