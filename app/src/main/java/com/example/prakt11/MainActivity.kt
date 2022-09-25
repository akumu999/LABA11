package com.example.prakt11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private var costList: MutableList<Expenses> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_add = findViewById<Button>(R.id.add_button)
        val btn_show = findViewById<Button>(R.id.show_button)
        val name = findViewById<EditText>(R.id.editText_name)
        val cost = findViewById<EditText>(R.id.editText_cost)
        getExpense()
        btn_add.setOnClickListener(View.OnClickListener {
            if (name.text.toString()!="" && cost.text.toString()!="")
            {
                val temp: Expenses = Expenses(name.text.toString(), cost.text.toString().toInt())
                addExpense(name.text.toString(), cost.text.toString().toInt())
                Toast.makeText(this, "Успешно добавлено", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this, "Нельзя добавить пустую строку", Toast.LENGTH_SHORT).show()
            }
        })
        btn_show.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        })
    }

    private fun getExpense(){
        val preferences = getSharedPreferences("pref", MODE_PRIVATE)
        var json: String = ""
        if (!preferences.contains("json"))
        {
            return
        } else {
            json = preferences.getString("json", "NOT_JSON").toString()
        }
        val temp = Gson().fromJson<List<Expenses>>(json, object: TypeToken<List<Expenses>>(){}.type)
        costList.addAll(temp)
    }

    private fun addExpense(name: String, cost: Int)
    {
        val temp: Expenses = Expenses(name, cost)
        costList.add(temp)
        val preferences = getSharedPreferences("pref", MODE_PRIVATE)
        preferences.edit {
            this.putString("json", Gson().toJson(costList).toString())
        }
    }

}
