package com.soumya.myandroidtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.soumya.myandroidtest.R
import com.soumya.myandroidtest.databinding.ImageShowLayoutBinding
import com.soumya.myandroidtest.model.ImageShowData

class ImageShowAdapter(private val onItemClick: (ImageShowData) -> Unit) : PagingDataAdapter<ImageShowData,
        ImageShowAdapter.ImageViewHolder>(diffCallback) {


    inner class ImageViewHolder(
        val binding: ImageShowLayoutBinding
    ) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ImageShowData>() {
            override fun areItemsTheSame(oldItem: ImageShowData, newItem: ImageShowData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageShowData, newItem: ImageShowData): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ImageShowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val data = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                tvTitle.text = "${data?.author}"
                tvDesc.text = context.getString(R.string.no_data)

                val imageLink = data?.download_url
                imageView.load(imageLink) {
                    crossfade(true)
                    crossfade(1000)
                }
                holder.itemView.setOnClickListener {

                    data?.let { it1 -> onItemClick.invoke(it1) }
                }
            }
        }

    }


}