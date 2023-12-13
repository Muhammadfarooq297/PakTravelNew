package com.example.paktravelnew.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paktravelnew.Models.RoomModel
import com.example.paktravelnew.Models.foodModel
import com.example.paktravelnew.databinding.FoodItemBinding
import com.example.paktravelnew.databinding.RoomItemBinding
import com.example.paktravelnew.foodDetailsActivity
import com.example.paktravelnew.roomDetailsActivity
import com.google.firebase.database.DatabaseReference

class roomAdapter (private val context: Context, private val roomList: ArrayList<RoomModel>, databaseReference: DatabaseReference):
    RecyclerView.Adapter<roomAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = RoomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        val roomItem=roomList[position]
        val uriString=roomItem.roomImages
//        val uri=Uri.parse(uriString)
        holder.bind(position)
        holder.itemView.setOnClickListener{
            val intent= Intent(context, roomDetailsActivity::class.java)
            intent.putExtra("RoomType",roomItem.roomType)
            intent.putExtra("RoomImage",uriString)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = roomList.size

    inner class MenuViewHolder(private val binding: RoomItemBinding) :
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
                val roomItem = roomList[position]
                val uriString = roomItem.roomImages
                val uri = Uri.parse(uriString)
                roomTypeText.text = roomItem.roomType
                roomChargesText.text = roomItem.roomCharges_perDay
                Glide.with(context).load(uri).into(popularimageView)


            }
        }

    }
}