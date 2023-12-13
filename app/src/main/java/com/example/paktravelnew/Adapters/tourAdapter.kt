package com.example.paktravelnew.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paktravelnew.DetailsActivity
import com.example.paktravelnew.Fragments.TourBottomSheetFragment
import com.example.paktravelnew.Models.TourModel
import com.example.paktravelnew.databinding.TourItemBinding
import com.google.firebase.database.DatabaseReference

class tourAdapter (private val context: Context,private val tourList:ArrayList<TourModel>,databaseReference:DatabaseReference):
    RecyclerView.Adapter<tourAdapter.MenuViewHolder>() {
    private val itemClickListener: OnClickListener ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding=TourItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

        val tourItem=tourList[position]
        val uriString=tourItem.tourImage
//        val uri=Uri.parse(uriString)
        holder.bind(position)
        holder.itemView.setOnClickListener{
            val intent= Intent(context, DetailsActivity::class.java)
            intent.putExtra("MenuItemName",tourItem.tourName)
            intent.putExtra("MenuItemImage",uriString)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int =tourList.size

    inner class MenuViewHolder(private val binding:TourItemBinding): RecyclerView.ViewHolder(binding.root) {

//        init {
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
        fun bind(position: Int) {
            binding.apply {
                val tourItem=tourList[position]
                val uriString=tourItem.tourImage
                val uri=Uri.parse(uriString)
                foodNamePopular.text=tourItem.tourName
                pricePopular.text=tourItem.tourCost
                Glide.with(context).load(uri).into(popularimageView)



            }

        }


    }
    interface OnClickListener {

        fun onItemClick(position: Int)
        {

        }

    }


}