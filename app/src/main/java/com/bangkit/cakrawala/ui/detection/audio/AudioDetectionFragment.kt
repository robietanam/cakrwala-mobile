package com.bangkit.cakrawala.ui.detection.audio

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.cakrawala.R

class AudioDetectionFragment : Fragment() {

    companion object {
        fun newInstance() = AudioDetectionFragment()
    }

    private lateinit var viewModel: AudioDetectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_audio_detection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AudioDetectionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}