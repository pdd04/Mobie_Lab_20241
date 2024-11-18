package vn.edu.hust.studentman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(
  private val students: MutableList<StudentModel>,
  private val onEditClick: (StudentModel, Int) -> Unit,
  private val onDeleteClick: (Int) -> Unit  // Callback cho nút xóa
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false)
    return StudentViewHolder(view)
  }

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]
    holder.bind(student)

    // Gán sự kiện click cho nút Edit
    holder.itemView.findViewById<ImageView>(R.id.image_edit).setOnClickListener {
      onEditClick(student, position)
    }

    // Gán sự kiện click cho nút Delete
    holder.itemView.findViewById<ImageView>(R.id.image_remove).setOnClickListener {
      onDeleteClick(position)  // Gọi callback xóa
    }
  }

  override fun getItemCount(): Int = students.size

  inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(student: StudentModel) {
      itemView.findViewById<TextView>(R.id.text_student_name).text = student.name
      itemView.findViewById<TextView>(R.id.text_student_id).text = student.id
    }
  }

  // Hàm hiển thị Snackbar với tùy chọn Undo khi xóa sinh viên
  fun showUndoSnackbar(view: View, position: Int, recentlyDeletedStudent: StudentModel?, recentlyDeletedPosition: Int) {
    Snackbar.make(view, "Student deleted", Snackbar.LENGTH_INDEFINITE)
      .setAction("UNDO") {
        // Khôi phục lại sinh viên nếu có
        recentlyDeletedStudent?.let { student ->
          students.add(recentlyDeletedPosition, student)
          notifyItemInserted(recentlyDeletedPosition)
        }
      }
      .show()
  }
}

