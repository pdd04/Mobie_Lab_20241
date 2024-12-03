package vn.edu.hust.studentman

import android.app.Activity
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

    private val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002")
    )

    private lateinit var studentAdapter: ArrayAdapter<String>
    private lateinit var listView: ListView

    companion object {
        const val REQUEST_ADD = 1
        const val REQUEST_EDIT = 2
        const val EXTRA_STUDENT = "student"
        const val EXTRA_POSITION = "position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo ListView và Adapter
        listView = findViewById(R.id.list_view_students)
        studentAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            students.map { "${it.name} - ${it.id}" }
        )
        listView.adapter = studentAdapter

        // Đăng ký Context Menu cho ListView
        registerForContextMenu(listView)
    }

    // Tạo OptionMenu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Xử lý chọn OptionMenu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                val intent = Intent(this, AddEditStudentActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Tạo Context Menu
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    // Xử lý chọn Context Menu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.menu_edit -> {
                val student = students[info.position]
                val intent = Intent(this, AddEditStudentActivity::class.java)
                intent.putExtra(EXTRA_STUDENT, student)
                intent.putExtra(EXTRA_POSITION, info.position)
                startActivityForResult(intent, REQUEST_EDIT)
                true
            }
            R.id.menu_remove -> {
                students.removeAt(info.position)
                updateListView()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    // Nhận kết quả từ AddEditStudentActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val student = data.getParcelableExtra<StudentModel>(EXTRA_STUDENT) ?: return
            when (requestCode) {
                REQUEST_ADD -> students.add(student)
                REQUEST_EDIT -> {
                    val position = data.getIntExtra(EXTRA_POSITION, -1)
                    if (position != -1) {
                        students[position] = student
                    }
                }
            }
            updateListView()
        }
    }

    // Cập nhật ListView
    private fun updateListView() {
        studentAdapter.clear()
        studentAdapter.addAll(students.map { "${it.name} - ${it.id}" })
        studentAdapter.notifyDataSetChanged()
    }
}
