package com.example.paktravelnew.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paktravelnew.DetailsActivity
import com.example.paktravelnew.Models.TourModel
import com.example.paktravelnew.Models.foodModel
import com.example.paktravelnew.databinding.FoodItemBinding
import com.example.paktravelnew.databinding.TourItemBinding
import com.example.paktravelnew.foodDetailsActivity
import com.google.firebase.database.DatabaseReference

class foodAdapter(private val context: Context, private val foodList: ArrayList<foodModel>, databaseReference: DatabaseReference):
    RecyclerView.Adapter<foodAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        val foodItem=foodList[position]
        val uriString=foodItem.foodImage
//        val uri=Uri.parse(uriString)
        holder.bind(position)
        holder.itemView.setOnClickListener{
            val intent= Intent(context, foodDetailsActivity::class.java)
            intent.putExtra("MenuItemName",foodItem.foodName)
            intent.putExtra("MenuItemImage",uriString)
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = foodList.size

    inner class MenuViewHolder(private val binding: FoodItemBinding) :
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
                val foodItem = foodList[position]
                val uriString = foodItem.foodImage
                val uri = Uri.parse(uriString)
                foodNamePopular.text = foodItem.foodName
                pricePopular.text = foodItem.foodPrice
                Glide.with(context).load(uri).into(popularimageView)


            }
        }

    }
}