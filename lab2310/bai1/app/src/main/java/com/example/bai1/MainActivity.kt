package com.example.bai1

import android.R.attr.text
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var edtext: EditText
    lateinit var edtext2: EditText

    var baseCurrency:String = "FJD"
    var convertedToCurrency:String = "BBD"
    var conversionRate1: Double = 0.0
    var conversionRate2: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        edtext = findViewById(R.id.edtext)
        edtext2 = findViewById(R.id.edtext2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        spinnerSetup()
        textChangedStuff()
    }

    private fun textChangedStuff() {
        edtext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    getApiResult()
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Type a value", Toast.LENGTH_SHORT).show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("Main", "Before Text Changed")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Main", "OnTextChanged")
            }

        })
    }

    private fun getApiResult() {
        var api1 = "https://api.currencyfreaks.com/v2.0/rates/latest?apikey=20be60bf8a694ddb840895e47e181718&symbols=$convertedToCurrency&base=USD"
        var api2 = "https://api.currencyfreaks.com/v2.0/rates/latest?apikey=20be60bf8a694ddb840895e47e181718&symbols=$baseCurrency&base=USD"

        lifecycleScope.launch(Dispatchers.IO) {
            val apiResult1 = URL(api1).readText()
            val apiResult2 = URL(api2).readText()
            val jsonObject1 = JSONObject(apiResult1)
            val jsonObject2 = JSONObject(apiResult2)
            conversionRate1 =
                jsonObject2.getJSONObject("rates").getString(baseCurrency).toDouble()
            conversionRate2 =
                jsonObject1.getJSONObject("rates").getString(convertedToCurrency).toDouble()


            withContext(Dispatchers.Main) {
                val text = ((edtext.text.toString().toDouble()) * (1/conversionRate1) * conversionRate2).toString()
                edtext2.setText(text)
            }
        }
    }



    private fun spinnerSetup(){
        val spinner: Spinner = findViewById(R.id.spinner)
        val spinner2: Spinner = findViewById(R.id.spinner2)

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies1,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter

        }

        ArrayAdapter.createFromResource(
            this,
            R.array.currencies2,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner2.adapter = adapter

        }

        spinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }

        })

        spinner2.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertedToCurrency = parent?.getItemAtPosition(position).toString()
                getApiResult()
            }

        })
    }
}