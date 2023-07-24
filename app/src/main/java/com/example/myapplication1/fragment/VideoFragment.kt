package com.example.myapplication1.fragment

import android.app.Activity
import android.content.Intent
import android.widget.MediaController
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication1.databinding.FragmentVideoBinding
import com.example.myapplication1.viewmodel.VideoUploadViewModel

class VideoFragment : Fragment() {
    private lateinit var binding: FragmentVideoBinding
    private lateinit var videoUploadViewModel: VideoUploadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        val uploadButton = binding.btnUpload
        videoUploadViewModel = ViewModelProvider(this).get(VideoUploadViewModel::class.java)

        uploadButton.setOnClickListener {
            selectVideoFromGallery()
        }
        return binding.root
    }

    private fun selectVideoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_SELECT_VIDEO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SELECT_VIDEO && resultCode == Activity.RESULT_OK) {
            val videoUri = data?.data
            videoUri?.let {
                videoUploadViewModel.uploadVideoToGallery(requireContext(), it)
                playVideoFromUri(it)
            }
        }
    }

    private fun playVideoFromUri(videoUri: Uri) {
        val videoView = binding.videoView
        videoView.setVideoURI(videoUri)

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()
    }


    companion object {
        private const val REQUEST_SELECT_VIDEO = 1

    }
}