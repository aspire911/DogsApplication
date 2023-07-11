package com.mdmx.dogsapplication.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mdmx.dogsapplication.R
import com.mdmx.dogsapplication.common.Constants
import com.mdmx.dogsapplication.common.Constants.LIBRARY
import com.mdmx.dogsapplication.common.Constants.CROSSFADE_DURATION
import com.mdmx.dogsapplication.databinding.ItemImageBinding
import com.squareup.picasso.Picasso


class ImageGridAdapter(private val imageSize: Int) : RecyclerView.Adapter<ImageGridAdapter.ImageViewHolder>() {

    private val items = ArrayList<String>()

    fun addItems(newItems: List<String>) {
        val diffCallback = ImageDiffCallback(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding: ItemImageBinding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) =
        holder.bind(items[position])

    inner class ImageViewHolder(private val itemBinding: ItemImageBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: String) {
            when (LIBRARY) {
                Constants.ImageDownloadingLibrary.COIL -> {
                    itemBinding.image.load(item) {
                        crossfade(true)
                        crossfade(CROSSFADE_DURATION)
                        error(R.drawable.error)
                        size(imageSize, imageSize)
                    }
                }

                Constants.ImageDownloadingLibrary.GLIDE -> {
                    Glide.with(itemBinding.image.context)
                        .load(item)
                        .override(imageSize, imageSize)
                        .error(R.drawable.error)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(itemBinding.image)
                }

                Constants.ImageDownloadingLibrary.PICASSO -> {
                    Picasso.with(itemBinding.image.context)
                        .load(item)
                        .error(R.drawable.error)
                        .resize(imageSize, imageSize)
                        .into(itemBinding.image)
                }
            }
        }
    }

    class ImageDiffCallback(private val oldList: List<String>, private val newList: List<String>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition] == newList[newPosition]
        }
    }
}