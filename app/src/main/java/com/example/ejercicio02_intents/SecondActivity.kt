package com.example.ejercicio02_intents

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.ejercicio02_intents.databinding.ActivityMainBinding
import com.example.ejercicio02_intents.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_second)

        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.title = "Atrás"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        if (bundle != null && bundle.getString("saludo") != null){
            val saludo = bundle.getString("saludo")
            binding.textViewIntent.text = saludo
        }else{
            Toast.makeText(this,"Esta vacío", Toast.LENGTH_SHORT).show()
        }

        binding.btnSiguiente2.setOnClickListener {
            startActivity(this,ThirdActivity::class.java)
        }
    }

    fun startActivity(activity : Activity, nextActivity:Class<*>){
        val intent = Intent(activity,nextActivity)
        //intent.putExtra("saludo",saludo)
        activity.startActivity(intent)
        activity.finish()
    }
}