package com.example.myapplication1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication1.databinding.ViewRecyclerBinding
import com.example.myapplication1.model.FavourModel

class RecycleAdapter(val context: Context) : RecyclerView.Adapter<RecycleAdapter.HomeViewHolder>() {

    var dashboardListener: ((locationModel: FavourModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ViewRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        try {
            val statusModel = differ.currentList[position]
            holder.setView(statusModel)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class HomeViewHolder(private var binding: ViewRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setView(sample: FavourModel) {

            val final = sample.img1!!.replace("..", "")
            Glide.with(context).load(final).into(binding.ivView)
            Log.i("TAG", "instantiateItem:$final ")
            binding.tvArea.text = sample.area
            binding.tvCity.text = sample.city
            binding.tvPrice.text = sample.price_in_number
            binding.tvType.text = sample.pro_type
            binding.tvProduct.text = sample.product_title
            binding.tvId.text = sample.id

        }

        init {

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    try {
                        dashboardListener?.invoke(differ.currentList[position])

                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                    }
                }
            }

        }

    }

    private val callback = object : DiffUtil.ItemCallback<FavourModel>() {
        override fun areItemsTheSame(oldItem: FavourModel, newItem: FavourModel): Boolean {
            return oldItem.city == newItem.city
        }

        override fun areContentsTheSame(oldItem: FavourModel, newItem: FavourModel): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, callback)

}