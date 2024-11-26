package com.example.studentmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class AddOrEditActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtStudentId: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or_edit)

        edtName = findViewById(R.id.edit_student_name)
        edtStudentId = findViewById(R.id.edit_student_id)
        btnSave = findViewById(R.id.btn_save_student)

        val student = intent.getSerializableExtra("student") as? Student
        val position = intent.getIntExtra("position", -1)

        student?.let {
            edtName.setText(it.name)
            edtStudentId.setText(it.studentId)
        }

        // Xử lý sự kiện khi nhấn Save
        btnSave.setOnClickListener {
            val name = edtName.text.toString().trim()
            val studentId = edtStudentId.text.toString().trim()

            // Kiểm tra đầu vào hợp lệ
            if (name.isEmpty() || studentId.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tạo Intent để trả kết quả về MainActivity
            val resultIntent = Intent().apply {
                putExtra("student", Student(name, studentId))
                putExtra("position", position)
            }

            // Trả kết quả và đóng Activity
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}


