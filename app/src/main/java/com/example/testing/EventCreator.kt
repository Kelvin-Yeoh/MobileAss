package com.example.testing

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.ByteArrayOutputStream


class EventCreator : AppCompatActivity() {
    var editTextEventDescription: EditText? = null
    var editTextMeals: EditText? = null
    var browse: Button? = null
    var upload: Button? = null
    var img: ImageView? = null
    var bitmap: Bitmap? = null
    var encodeImageString: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.eventcreator_screen)
        img = findViewById<View>(R.id.img) as ImageView
        upload = findViewById<View>(R.id.upload) as Button
        browse = findViewById<View>(R.id.browse) as Button
        browse!!.setOnClickListener {
            Dexter.withActivity(this@EventCreator)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {}
                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }
                }).check()
        }
        upload!!.setOnClickListener { uploaddatatodb() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val filepath = data!!.data
            try {
                val inputStream = contentResolver.openInputStream(filepath!!)
                bitmap = BitmapFactory.decodeStream(inputStream)
                img!!.setImageBitmap(bitmap)
                encodeBitmapImage(bitmap)
            } catch (ex: Exception) {
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun encodeBitmapImage(bitmap: Bitmap?) {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val bytesofimage = byteArrayOutputStream.toByteArray()
        encodeImageString = Base64.encodeToString(bytesofimage, Base64.DEFAULT)
    }

    private fun uploaddatatodb() {
        editTextEventDescription = findViewById<View>(R.id.editTextEventDescription) as EditText
        editTextMeals = findViewById<View>(R.id.editTextMeals) as EditText
        val name = editTextEventDescription!!.text.toString().trim { it <= ' ' }
        val dsg = editTextMeals!!.text.toString().trim { it <= ' ' }
        val request: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                editTextEventDescription!!.setText("")
                editTextMeals!!.setText("")
                img!!.setImageResource(R.drawable.ic_launcher_foreground)
                Toast.makeText(applicationContext, response, Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    applicationContext,
                    error.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val map: MutableMap<String, String> = HashMap()
                map["editTextEventDescription"] = name
                map["editTextMeals"] = dsg
                map["upload"] = encodeImageString!!
                return map
            }
        }
        val queue = Volley.newRequestQueue(applicationContext)
        queue.add(request)
    }

    companion object {
        private const val url = "http://10.0.2.2/eventcreate.php"
    }


    fun register(view: View?) {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
        finish()
    }

    fun login(view: View?) {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}