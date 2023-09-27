package com.example.paktravelnew.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.paktravelnew.Adapters.PopularAdapter
import com.example.paktravelnew.R
import com.example.paktravelnew.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val imageList=ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.banner4, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner5, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner6, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner7, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner8, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))

        val imageSlider=binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
//        imageSlider.setItemClickListener(object : ItemClickListener {
//            override fun doubleClick(position: Int) {
//
//            }
//
//            override fun onItemSelected(position: Int) {
//                val itemPosition=imageList[position]
//                val itemMessage="Selected Image $position"
//                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
//            }
//
//        })

        val foodNames= listOf("Hunza Valley Tour 2023-24 ","Skardu Valley Tour 2023-24"," Hunza Autumn Tour 2023-24","Hunza Cherry Blossom Tour")
        val foodPrices= listOf("$999","$1700","$899","$1000")
        val foodImages= listOf(R.drawable.hunzavalley,R.drawable.skardu,R.drawable.cherry_blossum,R.drawable.hunzavalley)
        val adapter= PopularAdapter(foodNames,foodPrices,foodImages,requireContext())
        binding.popularRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.popularRecyclerView.adapter=adapter
    }
    companion object {

    }
}