package com.example.paktravelnew.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paktravelnew.Models.PlaceModel
import com.example.paktravelnew.Models.RoomModel
import com.example.paktravelnew.PlacesDetailsActivity
import com.example.paktravelnew.databinding.PlacesItemBinding
import com.example.paktravelnew.databinding.RoomItemBinding
import com.example.paktravelnew.roomDetailsActivity
import com.google.firebase.database.DatabaseReference

class placeAdapter (private val context: Context, private val placeList: ArrayList<PlaceModel>, databaseReference: DatabaseReference):
    RecyclerView.Adapter<placeAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = PlacesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        val placeItem=placeList[position]
        val uriString=placeItem.placeImages
//        val uri=Uri.parse(uriString)
        holder.bind(position)
        holder.itemView.setOnClickListener{
            val intent= Intent(context, PlacesDetailsActivity::class.java)
            intent.putExtra("PlaceName",placeItem.placeName)
            intent.putExtra("PlaceImage",uriString)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = placeList.size

    inner class MenuViewHolder(private val binding: PlacesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {
            //            binding.root.setOnClickListener {
//                val position= adapterPosition
//                if(position!= RecyclerView.NO_POSITION)
//                {
//                    itemClickListener?.onItemClick(position)
//                }
//                //Set on Click listener to open details
//                val intent= Intent(requireContext,DetailsActivity::class.java)
//                intent.putExtra("MenuItemName",menuItems.get(position))
//                intent.putExtra("MenuItemImage",menuImages.get(position))
//                requireContext.startActivity(intent)
//
//
//            }
//        }

        }
        fun bind(position: Int) {

            binding.apply {
                val placeItem = placeList[position]
                val uriString = placeItem.placeImages
                val uri = Uri.parse(uriString)
                placeTypeText.text = placeItem.placeName
                Glide.with(context).load(uri).into(placeimageView)


            }
        }

    }
}