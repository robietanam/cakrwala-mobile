package com.bangkit.cakrawala.ui.detection.text

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.cakrawala.R
import com.bangkit.cakrawala.data.response.Auth
import com.bangkit.cakrawala.databinding.FragmentTextDetectionBinding
import com.bangkit.cakrawala.ui.AuthViewModelFactory
import com.bangkit.cakrawala.ui.HistoryViewModelFactory
import com.bangkit.cakrawala.ui.TextDetectionViewModelFactory
import com.bangkit.cakrawala.ui.common.TokenViewModel
import com.bangkit.cakrawala.ui.history.HistoryViewModel
import com.bangkit.cakrawala.utils.getImageUriTemp
import com.bangkit.cakrawala.utils.uriToFile
import java.io.File


class TextDetectionFragment : Fragment() {

    companion object {
        const val KEY_DATA = "key_data"
        fun newInstance(data: String): TextDetectionFragment {
            val fragment = TextDetectionFragment()
            val args = Bundle()
            args.putString(KEY_DATA, data)
            fragment.arguments = args
            return fragment
        }

        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
        private const val PICK_PDF_FILE = 2

        private const val TAG = "MapsActivity"
        const val ADD_STORY_KEY = "ADD_STORY_KEY"
    }

    private lateinit var viewModel: TextDetectionViewModel
    private lateinit var binding: FragmentTextDetectionBinding

    private var receivedData: String? = null
    private var currentImageUri: Uri? = null

    private var currentPdfUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        receivedData = arguments?.getString("key_data")

        binding = FragmentTextDetectionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Use the ViewModel

        viewModel = ViewModelProvider(this, TextDetectionViewModelFactory.getInstance(requireContext()))[TextDetectionViewModel::class.java]
        val tokenViewModel = ViewModelProvider(this, AuthViewModelFactory.getInstance(requireContext()))[TokenViewModel::class.java]

        tokenViewModel.getToken().observe(viewLifecycleOwner){

            if (it.premium == 1){
                binding.textInputLayout.counterMaxLength = 5000
            }
        }

        updateVisibility(R.id.rb_text)

        binding.radioGroup.setOnCheckedChangeListener { group, _ ->
            updateVisibility(group.checkedRadioButtonId)
        }

        binding.btnCamera.setOnClickListener {
            startCamera()
        }

        binding.btnGallery.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            setMyButtonLoading(it)
        }

        binding.btnPickFile.setOnClickListener {
            pickPDF.launch(arrayOf("application/pdf"))
        }

        binding.btnSubmit.setOnClickListener {
            submitForm()
        }

        viewModel.responseText.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "Success, checking text", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }


    }

    private fun setMyButtonLoading(value: Boolean){
        binding.btnSubmit.apply {
            setLoading(value)
            isEnabled = !value
        }
    }

    private fun submitForm() {

        when (viewModel.groupId.value) {
            R.id.rb_text ->uploadText()
            R.id.rb_pdf -> uploadPdf()
            R.id.rb_image -> uploadImage()
        }
    }

    private fun uploadText(){
        if (!binding.editDesc.text.isNullOrEmpty()){
            viewModel.uploadText(binding.editDesc.text.toString())
        }
    }

    private fun uploadPdf(){
        if (currentPdfUri != null){
            val file = uriToFile(currentPdfUri!!, requireContext(), isPdf = true)
            viewModel.uploadPdf(file)
        }
    }

    private fun uploadImage(){
        if (currentImageUri != null){

            val imageFile = uriToFile(currentImageUri!!, requireContext())
            viewModel.uploadImage(imageFile)
        }
    }


    private fun startCamera(){
        if (!isPermissionsGranted(CAMERA_PERMISSION)) {
            requestPermissionLauncher.launch(CAMERA_PERMISSION)
        } else {
            currentImageUri = getImageUriTemp(requireContext())
            launcherIntentCamera.launch(currentImageUri)
        }
    }

    private val pickPDF = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            currentPdfUri = uri
            showPreviewPdf()
        } else {
            Log.d("Pdf Picker", "No media selected")
        }
    }


    private val launcherGallery = registerForActivityResult(

        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private val launcherIntentCamera = registerForActivityResult(

        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }


    private fun showPreviewPdf() {
        currentPdfUri?.let {
            Log.d("Image URI", "showImage: $it")
            requireContext().contentResolver.query(it, null, null, null, null)
        }?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            cursor.moveToFirst()
            binding.tvFilename.text = cursor.getString(nameIndex)
        }

    }


    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imgPreview.setImageURI(it)
        }
    }

    private fun updateVisibility(id: Int) {
        viewModel.setGroupId(id)

        var groupId = 0
        when (viewModel.groupId.value) {
            R.id.rb_text -> groupId = R.id.group_text
            R.id.rb_pdf -> groupId = R.id.group_file
            R.id.rb_image -> groupId = R.id.group_image
        }

        val groups = listOf<Group>(binding.groupText, binding.groupFile, binding.groupImage)
        for (g in groups) {
            if (g.id == groupId) {
                g.visibility = View.VISIBLE
            } else {
                g.visibility = View.INVISIBLE
            }

        }

    }

    private fun isPermissionsGranted(permission: String) =
        ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED


    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(context, "Permission request granted", Toast.LENGTH_LONG).show()
                startCamera()
            } else {
                Toast.makeText(context, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }



}