package com.example.myapplication1.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication1.imageuploader.ImageUploader
import com.example.myapplication1.databinding.ActivityThreeBinding
import com.example.myapplication1.model.MultipartModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThreeBinding
    private lateinit var addProUploader: ImageUploader
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnNext.setOnClickListener {
            intent = Intent(applicationContext, FourActivity::class.java)
            startActivity(intent)
        }

        addProUploader = ImageUploader(this)

        binding.btnSelect.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openImagePicker()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        binding.btnSend.setOnClickListener {
            if (selectedImageUri != null) {
                uploadImage()
            } else {
                Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openImagePicker()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val selectedImage: Uri? = data.data
                    if (selectedImage != null) {
                        selectedImageUri = selectedImage
                        binding.ivView.setImageURI(selectedImageUri)
                        Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        selectImageLauncher.launch(intent)
    }

    private fun uploadImage() {
        val type = "14"
        val cid = "21472147"

        addProUploader.uploadImage(
            selectedImageUri!!,
            type,
            cid,
            object : Callback<MultipartModel> {
                override fun onResponse(
                    call: Call<MultipartModel>, response: Response<MultipartModel>
                ) {
                    if (response.isSuccessful) {
                        val addProResponse = response.body()
                        Toast.makeText(
                            this@ThirdActivity, addProResponse?.message, Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@ThirdActivity, "API error: " + response.code(), Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<MultipartModel>, t: Throwable) {
                    Toast.makeText(
                        this@ThirdActivity, "Network error: " + t.message, Toast.LENGTH_SHORT
                    ).show()
                    Log.i("TAG", "onFailureImage:$t")

                }

            })
    }
}