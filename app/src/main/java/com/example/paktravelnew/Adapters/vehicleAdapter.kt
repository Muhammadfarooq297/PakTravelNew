package com.example.paktravelnew.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paktravelnew.Models.RoomModel
import com.example.paktravelnew.Models.VehicleModel
import com.example.paktravelnew.databinding.RoomItemBinding
import com.example.paktravelnew.databinding.VehicleItemBinding
import com.example.paktravelnew.roomDetailsActivity
import com.example.paktravelnew.vehicleDetailsActivity
import com.google.firebase.database.DatabaseReference

class vehicleAdapter (private val context: Context, private val vehicleList: ArrayList<VehicleModel>, databaseReference: DatabaseReference):
    RecyclerView.Adapter<vehicleAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = VehicleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        val vehicleItem=vehicleList[position]
        val uriString=vehicleItem.vehicleImages
//        val uri=Uri.parse(uriString)
        holder.bind(position)
        holder.itemView.setOnClickListener{
            val intent= Intent(context, vehicleDetailsActivity::class.java)
            intent.putExtra("VehicleType",vehicleItem.vehicleType)
            intent.putExtra("VehicleImage",uriString)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = vehicleList.size

    inner class MenuViewHolder(private val binding: VehicleItemBinding) :
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
                val vehicleItem = vehicleList[position]
                val uriString = vehicleItem.vehicleImages
                val uri = Uri.parse(uriString)
                vehicleTypeText.text = vehicleItem.vehicleType
                vehicleChargesText.text = vehicleItem.vehicleCharges_perDay
                Glide.with(context).load(uri).into(popularimageView)


            }
        }

    }
}