package com.bangkit.cakrawala.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.cakrawala.R
import com.bangkit.cakrawala.databinding.FragmentHomeBinding
import com.bangkit.cakrawala.ui.detection.audio.AudioDetectionFragment
import com.bangkit.cakrawala.ui.detection.image.ImageDetectionFragment
import com.bangkit.cakrawala.ui.history.HistoryFragment
import com.bangkit.cakrawala.ui.detection.text.TextDetectionFragment

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private lateinit var historyFragment: HistoryFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val menuItemRv = binding.rvMenuItem

        // on below line we are initializing our list
        var menuList = ArrayList<MenuRvModal>()

        val layoutManager = GridLayoutManager(this.activity, 3)

        menuItemRv.layoutManager = layoutManager

        // on below line we are initializing our adapter
        val menuRVAdapter = MenuGridAdapter(menuList, this.requireContext())

        // on below line we are setting
        // adapter to our recycler view.
        menuItemRv.adapter = menuRVAdapter

        menuList.add(MenuRvModal(menuTitle = "Text", menuImage = R.drawable.format_quote, menuFragment = TextDetectionFragment.newInstance("GODDANG")))
        menuList.add(MenuRvModal(menuTitle = "Gambar", menuImage = R.drawable.no_image,menuFragment = AudioDetectionFragment()))
        menuList.add(MenuRvModal(menuTitle = "Audio", menuImage = R.drawable.mic, menuFragment = ImageDetectionFragment()))

        menuRVAdapter.notifyDataSetChanged()


        historyFragment = HistoryFragment()
        menuRVAdapter.setOnItemClickCallback(object : MenuGridAdapter.OnItemClickCallback {
            override fun onItemClicked(data: MenuRvModal) {
                parentFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.home_fragment_container_view, data.menuFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
        val fragmentHistory = childFragmentManager.findFragmentByTag(HistoryFragment::class.java.simpleName)

        if (fragmentHistory !is HistoryFragment) {
            childFragmentManager
                .beginTransaction()
                .replace(R.id.history_fragment_container_view, historyFragment, HistoryFragment::class.java.simpleName)
                .commit()
        }


    }


}