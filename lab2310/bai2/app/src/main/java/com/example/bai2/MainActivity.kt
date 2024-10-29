package com.example.bai2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale
import kotlin.random.Random
import java.text.SimpleDateFormat
import java.util.Date


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val avatar = arrayOf(R.drawable.avatar, R.drawable.avatar2, R.drawable.avatar3
            , R.drawable.avatar4)

        val currentDate = Date()

        val formatter = SimpleDateFormat("MM - dd", Locale.getDefault())
        val formattedDate = formatter.format(currentDate)

        val itemList = arrayListOf<ItemModel>()
        for (i in 1..27) {
            itemList.add(ItemModel("name $i","caption $i", "day la phan noi dung xem truoc cua email $i",formattedDate,avatar[Random.nextInt(0,3)]))
        }
        val listView = findViewById<ListView>(R.id.listview)
        listView.adapter = MyCustomAdapter(itemList)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

data class ItemModel(val username: String,val caption: String,val content: String, val date: String, val imageResource: Int)

class MyCustomAdapter(val items: ArrayList<ItemModel>): BaseAdapter() {
    override fun getCount(): Int = items.size
    override fun getItem(p0: Int): Any = items[p0]
    override fun getItemId(p0: Int): Long = p0.toLong()
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val row: View =
            LayoutInflater.from(p2?.context).inflate(
                R.layout.custom_row_icon_label, p2,
                false
            )



        val tvusername = row.findViewById<TextView>(R.id.tvusername)
        val tvcaption = row.findViewById<TextView>(R.id.tvcaption)
        val tvcontent = row.findViewById<TextView>(R.id.tvcontent)
        val date = row.findViewById<TextView>(R.id.date)

        val imageView = row.findViewById<ImageView>(R.id.icon)

        tvusername.text = items[p0].username
        tvcaption.text = items[p0].caption
        tvcontent.text = items[p0].content
        date.text = items[p0].date

        imageView.setImageResource(items[p0].imageResource)

        return row
    }
}