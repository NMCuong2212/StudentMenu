package com.example.student_menu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.CreationExtras

class AddStudentActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etMssv: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)

        etName = findViewById(R.id.etName)
        etMssv = findViewById(R.id.etMssv)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        btnSave = findViewById(R.id.btnSave)

        val student = intent.getSerializableExtra("student") as? Student
        val position = intent.getIntExtra("position", -1)

        student?.let {
            etName.setText(it.name)
            etMssv.setText(it.mssv)
            etEmail.setText(it.email)
            etPhone.setText(it.phone)
        }

        btnSave.setOnClickListener {
            val newStudent = Student(
                etName.text.toString(),
                etMssv.text.toString(),
                etEmail.text.toString(),
                etPhone.text.toString()
            )
            val resultIntent = Intent()
            resultIntent.putExtra("student", newStudent)
            if (intent.hasExtra("position")) {
                resultIntent.putExtra("position", intent.getIntExtra("position", -1))
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        }

    }

    override val defaultViewModelCreationExtras: CreationExtras
        get() = super.defaultViewModelCreationExtras
}