package com.example.myapplication1.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.myapplication1.imageuploader.ImageUploader
import com.example.myapplication1.databinding.FragmentThreeBinding
import com.example.myapplication1.model.MultipartModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ThirdFragment : Fragment() {
    private lateinit var binding: FragmentThreeBinding
    private lateinit var addProUploader: ImageUploader
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addProUploader = ImageUploader(requireContext())

        binding.btnSelect.setOnClickListener {
            val permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )

            val allPermissionsGranted = permissions.all {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }

            if (allPermissionsGranted) {
                openImagePicker()
            } else {
                requestPermissionLauncher.launch(permissions)
            }
        }

        binding.btnSend.setOnClickListener {
            if (selectedImageUri != null) {
                uploadImage()
            } else {
                Toast.makeText(requireContext(), "Please select an image first", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allPermissionsGranted = permissions.values.all { it }

            if (allPermissionsGranted) {
                openImagePicker()
            } else {
                Toast.makeText(requireContext(), "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val selectedImageUri: Uri? = intent.data
                    if (selectedImageUri != null) {
                        this.selectedImageUri = selectedImageUri
                        binding.ivView.setImageURI(selectedImageUri)
                        Toast.makeText(requireContext(), "Image selected", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else if (selectedImageUri != null) {
                    binding.ivView.setImageURI(selectedImageUri)
                    Toast.makeText(requireContext(), "Image selected", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun openImagePicker() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoUri = createPhotoUri()
        selectedImageUri = photoUri
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

        val chooserIntent = Intent.createChooser(galleryIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

        selectImageLauncher.launch(chooserIntent)
    }

    private fun createPhotoUri(): Uri? {
        val photoFileName = "photo.jpg"
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return storageDir?.let {
            FileProvider.getUriForFile(
                requireContext(),
                "com.example.myapplication1.fileprovider",
                File(it, photoFileName)
            )
        }
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
                            requireContext(), addProResponse?.message, Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(), "API error: " + response.code(), Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<MultipartModel>, t: Throwable) {
                    Toast.makeText(
                        requireContext(), "Network error: " + t.message, Toast.LENGTH_SHORT
                    ).show()
                    Log.i("TAG", "onFailureImage:$t")
                }
            })
    }
}