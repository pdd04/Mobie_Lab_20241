package com.example.studentmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import vn.edu.hust.studentman.MainActivity;

class AddEditStudentActivity : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        editName = findViewById(R.id.edit_student_name)
        editId = findViewById(R.id.edit_student_id)

        val student = intent.getParcelableExtra<StudentModel>(MainActivity.EXTRA_STUDENT)
                val position = intent.getIntExtra(MainActivity.EXTRA_POSITION, -1)

        // Hiển thị thông tin sinh viên (nếu có)
        student?.let {
            editName.setText(it.name)
            editId.setText(it.id)
        }

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            val name = editName.text.toString()
            val id = editId.text.toString()
            if (name.isNotBlank() && id.isNotBlank()) {
                val resultIntent = intent
                resultIntent.putExtra(MainActivity.EXTRA_STUDENT, StudentModel(name, id))
                resultIntent.putExtra(MainActivity.EXTRA_POSITION, position)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}