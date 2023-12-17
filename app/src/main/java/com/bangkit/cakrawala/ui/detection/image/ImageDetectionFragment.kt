package com.bangkit.cakrawala.ui.detection.image

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.cakrawala.R

class ImageDetectionFragment : Fragment() {

    companion object {
        fun newInstance() = ImageDetectionFragment()
    }

    private lateinit var viewModel: ImageDetectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image_detection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ImageDetectionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}