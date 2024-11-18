package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

  private lateinit var studentAdapter: StudentAdapter
  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )

  private var recentlyDeletedStudent: StudentModel? = null  // Lưu trữ sinh viên vừa bị xóa
  private var recentlyDeletedPosition: Int = -1  // Lưu trữ vị trí của sinh viên vừa bị xóa

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Khởi tạo adapter với callback chỉnh sửa và xóa
    studentAdapter = StudentAdapter(students, { student, position ->
      showEditStudentDialog(student, position)
    }, { position ->
      showDeleteConfirmationDialog(position)
    })

    // Gán adapter và layoutManager cho RecyclerView
    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddStudentDialog()
    }

  }

  private fun showDeleteConfirmationDialog(position: Int) {
    val studentToDelete = students[position]
    AlertDialog.Builder(this)
      .setTitle("Confirm Delete")
      .setMessage("Are you sure you want to delete this student?")
      .setPositiveButton("Yes") { _, _ ->
        // Lưu trữ sinh viên bị xóa
        recentlyDeletedStudent = studentToDelete
        recentlyDeletedPosition = position

        // Xóa sinh viên khỏi danh sách
        students.removeAt(position)
        studentAdapter.notifyItemRemoved(position)

        // Hiển thị snackbar với tùy chọn Undo
        showUndoSnackbar(position)
      }
      .setNegativeButton("No", null)
      .show()
  }

  private fun showUndoSnackbar(position: Int) {
    val rootView = findViewById<View>(android.R.id.content)

    Snackbar.make(rootView, "Student deleted", Snackbar.LENGTH_INDEFINITE)
      .setAction("UNDO") {
        // Khôi phục lại sinh viên
        recentlyDeletedStudent?.let { student ->
          students.add(recentlyDeletedPosition, student)
          studentAdapter.notifyItemInserted(recentlyDeletedPosition)
        }
      }
      .show()
  }


  private fun showAddStudentDialog() {
    val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)
    val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
    val editId = dialogView.findViewById<EditText>(R.id.edit_student_id)

    AlertDialog.Builder(this)
      .setTitle("Add New Student")
      .setView(dialogView)
      .setPositiveButton("Add") { _, _ ->
        val name = editName.text.toString()
        val id = editId.text.toString()
        if (name.isNotBlank() && id.isNotBlank()) {
          students.add(StudentModel(name, id))
          studentAdapter.notifyDataSetChanged()
        }
      }
      .setNegativeButton("Cancel", null)
      .show()
  }

  private fun showEditStudentDialog(student: StudentModel, position: Int) {
    val dialogView = layoutInflater.inflate(R.layout.dialog_add_student, null)
    val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
    val editId = dialogView.findViewById<EditText>(R.id.edit_student_id)

    // Hiển thị thông tin sinh viên hiện tại
    editName.setText(student.name ?: "") // Nếu name null, thay bằng chuỗi rỗng
    editId.setText(student.id ?: "") // Nếu id null, thay bằng chuỗi rỗng

    AlertDialog.Builder(this)
      .setTitle("Edit Student")
      .setView(dialogView)
      .setPositiveButton("Save") { _, _ ->
        val updatedName = editName.text.toString()
        val updatedId = editId.text.toString()
        if (updatedName.isNotBlank() && updatedId.isNotBlank()) {
          students[position] = StudentModel(updatedName, updatedId)
          studentAdapter.notifyItemChanged(position)
        }
      }
      .setNegativeButton("Cancel", null)
      .show()
  }
}


