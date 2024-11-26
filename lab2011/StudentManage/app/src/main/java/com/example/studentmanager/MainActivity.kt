package com.example.studentmanager

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var studentListView: ListView
    private lateinit var studentAdapter: ArrayAdapter<String>
    private val students = mutableListOf<Student>(
            Student("Nguyễn Văn An", "001"),
            Student("Trần Thị Bảo", "002"),
            Student("Lê Hoàng Cường", "003"),
            Student("Phạm Minh Duy", "004"),
            Student("Nguyễn Thị Mai", "005"),
            Student("Lê Quốc Toàn", "006"),
            Student("Trần Văn Nam", "007"),
            Student("Hoàng Thị Lan", "008"),
            Student("Vũ Minh Tâm", "009"),
            Student("Nguyễn Thị Lan", "010"),
            Student("Lê Anh Tuấn", "011"),
            Student("Trần Quang Huy", "012"),
            Student("Nguyễn Thị Kim", "013"),
            Student( "Bùi Ngọc Duy",  "014"),
            Student("Trần Đình Vũ", "015"),
            Student("Lê Quang Bình", "016"),
            Student("Nguyễn Thị Hoa", "017"),
            Student("Phan Thanh Tùng", "018"),
            Student("Trần Minh Quang", "019"),
            Student("Vũ Thị Lan", "020"),
            Student("Đặng Minh Châu", "021"),
            Student("Nguyễn Thị Mai Lan", "022"),
            Student("Phan Thị Vân", "023"),
            Student("Bùi Hoàng Tú", "024"),
            Student("Lê Minh Tú", "025"),
            Student("Nguyễn Minh Đức", "026"),
            Student("Hoàng Quang Duy", "027"),
            Student("Trần Đình Cường", "028"),
            Student("Nguyễn Minh Tân", "029"),
            Student("Lê Thị Lan", "030")
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentListView = findViewById(R.id.studentListView)

        // Thiết lập adapter
        studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students.map { "${it.name}\n${it.studentId}" })
        studentListView.adapter = studentAdapter

        // Đăng ký ContextMenu cho ListView
        registerForContextMenu(studentListView)

        // Xử lý khi nhấn vào sinh viên trong danh sách
        studentListView.setOnItemClickListener { _, _, position, _ ->
            editStudent(position)
        }
    }

    // Thiết lập OptionMenu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    // Xử lý khi chọn mục trong OptionMenu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addNew -> {
                // Mở Activity thêm mới sinh viên
                val intent = Intent(this, AddOrEditActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Thiết lập ContextMenu
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    // Xử lý khi chọn mục trong ContextMenu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            R.id.edit -> editStudent(info.position) // Chỉnh sửa
            R.id.remove -> {
                students.removeAt(info.position) // Xóa
                updateStudentList()
            }
        }
        return super.onContextItemSelected(item)
    }

    // Mở Activity để chỉnh sửa thông tin sinh viên
    private fun editStudent(position: Int) {
        val intent = Intent(this, AddOrEditActivity::class.java).apply {
            putExtra("student", students[position])
            putExtra("position", position)
        }
        startActivityForResult(intent, REQUEST_EDIT)
    }

    // Cập nhật lại danh sách sinh viên
    private fun updateStudentList() {
        studentAdapter.clear()
        studentAdapter.addAll(students.map { "${it.name} - ${it.studentId}" })
        studentAdapter.notifyDataSetChanged()
    }

    // Nhận kết quả từ Activity thêm/sửa
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val student = data.getSerializableExtra("student") as? Student
            val position = data.getIntExtra("position", -1)

            when (requestCode) {
                REQUEST_ADD -> {
                    student?.let { students.add(it) }
                }
                REQUEST_EDIT -> {
                    if (position >= 0 && student != null) {
                        students[position] = student
                    }
                }
            }
            updateStudentList()
        }
    }


    companion object {
        const val REQUEST_ADD = 1
        const val REQUEST_EDIT = 2
    }
}



