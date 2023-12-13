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
import com.example.paktravelnew.databinding.PopularitemBinding
import com.google.firebase.database.DatabaseReference

class PopularAdapter (private val context: Context, private val tourList:ArrayList<TourModel>, databaseReference: DatabaseReference) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    private val itemClickListener: tourAdapter.OnClickListener?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularitemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
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

    inner class PopularViewHolder(private val binding:PopularitemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val tourItem=tourList[position]
                val uriString=tourItem.tourImage
                val uri= Uri.parse(uriString)
                foodNamePopular.text=tourItem.tourName
                pricePopular.text=tourItem.tourCost
                Glide.with(context).load(uri).into(popularimageView)


            }

        }

    }

}