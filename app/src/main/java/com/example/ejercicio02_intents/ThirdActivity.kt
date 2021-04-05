package com.example.ejercicio02_intents

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.ejercicio02_intents.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private val PHONE_CODE = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val binding=ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.title = "Atrás"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.imageButtonPhone.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val phoneNumber = binding.editTextPhone.text.toString()

                if (!phoneNumber.isEmpty()){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (checarPermiso(android.Manifest.permission.CALL_PHONE)) {
                            val intentAceptado = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
                            if (ActivityCompat.checkSelfPermission(this@ThirdActivity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return
                            }
                            startActivity(intentAceptado)
                        } else {
                            if (!shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)) {
                                requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE),PHONE_CODE)
                            } else {
                                Toast.makeText(this@ThirdActivity,"Favor de habilitar el permiso correspondiente para continuar",Toast.LENGTH_LONG).show()

                                val intentSettings =Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)

                                intentSettings.addCategory(Intent.CATEGORY_DEFAULT)
                                intentSettings.data = Uri.parse("package:$packageName")
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                                intentSettings.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                                startActivity(intentSettings)
                            }
                        }
                    }
                    else {
                        versionAntigua(phoneNumber)
                    }
                }
                else {
                    Toast.makeText(this@ThirdActivity,"Debes de marcar un número, intenta nuevamente",Toast.LENGTH_LONG).show()
                }
            }})

        binding.imageButtonWeb.setOnClickListener {
            val url = binding.editTextWeb.text.toString()
            val intentWeb = Intent()
            intentWeb.action=Intent.ACTION_VIEW
            intentWeb.data= Uri.parse("http://$url")
            startActivity(intentWeb)
        }

        binding.buttonEmailMe.setOnClickListener {
            val email="gchinacortez@gmail.com"
            val intentEmail = Intent(Intent.ACTION_SEND,Uri.parse(email))
            //intentEmail.type="plain/text"
            intentEmail.putExtra(Intent.EXTRA_SUBJECT,"Título del email")
            intentEmail.putExtra(Intent.EXTRA_TEXT,"Hola, estoy esperando la respuesta...")
            intentEmail.putExtra(Intent.EXTRA_EMAIL, arrayOf("gabrielignacio.china.ce84@dgeti.sems.gob.mx",
                    "gchinacortez@gmail.com"))
            intentEmail.type = "text/plain"
            startActivity(Intent.createChooser(intentEmail,"Elige el cliente de correo..."))
        }

        binding.imageButtonCamera.setOnClickListener {
            val intentCamera = Intent("android.media.action.IMAGE_CAPTURE")
            startActivity(intentCamera)
        }

        binding.buttonContactPhone.setOnClickListener {
            val intentCall = Intent(Intent.ACTION_DIAL,Uri.parse("tel:3141032854"))
            startActivity(intentCall)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.menuContactos -> {
                val intentContactos = Intent(Intent.ACTION_VIEW,Uri.parse("content://contacts/people"))
                startActivity(intentContactos)
            }

            R.id.menuMensaje -> {
                val intentSMS = Intent()
                intentSMS.action = Intent.ACTION_SENDTO
                intentSMS.data = Uri.parse("smsto:")
                //Colocar el numero si lo vas a enviar a alguien en especifico
                //intentSMS.putExtra("address", "8341381216")
                intentSMS.putExtra("sms_body","Cuerpo del SMS desde un menú")
                startActivity(intentSMS)
            }

            R.id.menuVideo -> {
                val intentVideo = Intent("android.media.action.VIDEO_CAPTURE")
                startActivity(intentVideo)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun checarPermiso(permission: String):Boolean{
        val result = this.checkCallingOrSelfPermission(permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun versionAntigua(phoneNumber: String){
        val intentCall = Intent(Intent.ACTION_CALL,Uri.parse("tel:$phoneNumber"))
        if(checarPermiso(android.Manifest.permission. CALL_PHONE)){
            if (ActivityCompat.checkSelfPermission(this@ThirdActivity, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                return
            }
            startActivity(intentCall)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PHONE_CODE -> {

                val permiso = permissions[0]
                val resultado = grantResults[0]

                val editTextPhone = findViewById<EditText>(R.id.editTextPhone)

                if (permiso == android.Manifest.permission.CALL_PHONE) {
                    //Toast.makeText(this, "$resultado", Toast.LENGTH_LONG).show()
                    if (resultado == PackageManager.PERMISSION_GRANTED) {
                        val phoneNumber = editTextPhone!!.text.toString()
                        val intentCall = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))

                        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
                            return
                        }
                        startActivity(intentCall)
                    } else {
                        Toast.makeText(this, "Has denegado el permiso", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}