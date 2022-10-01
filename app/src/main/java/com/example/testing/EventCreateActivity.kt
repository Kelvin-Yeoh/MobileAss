package com.example.testing

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.testing.databinding.FragmentDonationBinding
import com.example.testing.databinding.FragmentEventCreateActivityBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.ByteArrayOutputStream


class EventCreateActivity : Fragment() {

    private lateinit var binding : FragmentEventCreateActivityBinding
    var editTextEventDescription: EditText? = null
    var editTextMeals: EditText? = null
    var editTextEventTitle: EditText? = null
    var browse: Button? = null
    var upload: Button? = null
    var img: ImageView? = null
    var bitmap: Bitmap? = null
    var encodeImageString: String? = null
    var currentMealint: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_create_activity, container, false)

        img = binding.img
        upload = binding.upload
        browse = binding.browse
        browse!!.setOnClickListener {
            Dexter.withActivity(activity)
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
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            val filepath = data!!.data
            try {
                val inputStream = requireActivity().contentResolver.openInputStream(filepath!!)
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

        editTextEventDescription = binding.editTextEventDescription
        editTextMeals = binding.editTextMeals
        editTextEventTitle = binding.editTextEventTitle
        val currentMeal = currentMealint.toString().trim { it <= ' ' }

        val title = editTextEventTitle!!.text.toString().trim { it <= ' ' }
        val name = editTextEventDescription!!.text.toString().trim { it <= ' ' }
        val meal = editTextMeals!!.text.toString().trim { it <= ' ' }

        val request: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                editTextEventDescription!!.setText("")
                editTextMeals!!.setText("")
                img!!.setImageResource(R.drawable.ic_launcher_foreground)
                Toast.makeText(binding.root.context, response, Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    binding.root.context,
                    error.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val map: MutableMap<String, String> = HashMap()
                map["editTextEventDescription"] = name
                map["editTextMeals"] = meal
                map["editTextEventTitle"] = title
                map["currentMeal"] = currentMeal
                map["upload"] = encodeImageString!!
                return map
            }
        }
        val queue = Volley.newRequestQueue(binding.root.context)
        queue.add(request)
    }

    companion object {
        private const val url = "http://10.0.2.2/eventcreate.php"
    }
}