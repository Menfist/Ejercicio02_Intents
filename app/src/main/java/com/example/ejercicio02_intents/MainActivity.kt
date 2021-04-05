package com.example.ejercicio02_intents

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejercicio02_intents.databinding.ActivityMainBinding
import java.util.*

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val saludo="Hola desde ActivityMain"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setIcon(R.mipmap.ic_launcher_app3)

        binding.btnCalcular.setOnClickListener {
            val añoNac:Int = binding.editTextNumber.text.toString().toInt()
            val añoActual = Calendar.getInstance().get(Calendar.YEAR)
            val miEdad = añoActual - añoNac

            binding.textView.text="Tu edad es $miEdad años"
        }

        binding.btnSiguiente.setOnClickListener {
            startActivity(this,SecondActivity::class.java)
        }
    }

    fun startActivity(activity : Activity, nextActivity:Class<*>){
        val intent = Intent(activity,nextActivity)
        intent.putExtra("saludo",saludo)
        activity.startActivity(intent)
        activity.finish()
    }
}