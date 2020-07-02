package ui

import android.content.pm.ActivityInfo
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Double
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.URL

var s1 = ""
var s3 = ""
var textView: TextView
    get() {
        TODO()
    }
    set(value) {}
var editTextNumberDecimal: EditText
    get() {
        TODO()
    }
    set(value) {}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // Инициализация массива
        val c = arrayOf(
            "EUR",
            "RUB",
            "USD",
            "GBP",
            "JPY",
            "BTC",
            "BCH",
            "XRP"
        )
        // Инициализация адаптера
        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            c // Array
        )
        // дроп доун ресурс
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spinner.adapter = adapter;
        spinner3.adapter = adapter;

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                s1 = c[position] // Выбрали валюту, из которой переводим
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        spinner3.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                s3 = c[position] // Выбрали валюту, в которую переводим
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }
    class getData() : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String? {
            var result = ""
            if(s1==s3){

                result = "1.00"
            } else{
                var url = "https://currate.ru/api/?get=rates&pairs=" + s1 + s3 + "&key=fbebfc3cc40881c10be5b0030b5502b8"

                try {
                    result = URL(url).readText()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            return result
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }
    }

    fun Click(view: View) {
        val Get = getData()
        Get.execute()
        var stroca = Get.get()
        stroca = stroca.dropLast(3)
        stroca = stroca.substringAfterLast('"')
        var coefficient = stroca.toDouble()
        var user = editTextNumberDecimal.text.toString()
        if(user == "") textView.setText("Ошибка, ничего не введено")
        else{
            coefficient =  user.toDouble() * coefficient
            var bd = BigDecimal(Double.toString(coefficient))
            bd = bd.setScale(3, RoundingMode.HALF_UP)
            textView.setText(bd.toString())
        }


    }
}
