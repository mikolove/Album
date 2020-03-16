package com.mikolove.album.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mikolove.album.data.entities.Album
import com.mikolove.album.databinding.ItemDetailBinding

class DetailAdapter(val clickListener: DetailClickListener) : ListAdapter<Album, DetailViewHolder>(DetailDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(clickListener,getItem(position))
    }
}
class DetailViewHolder(val binding : ItemDetailBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(clickListener: DetailClickListener, item: Album) {
        binding.album = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object{
        fun from(parent : ViewGroup) : DetailViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemDetailBinding.inflate(layoutInflater,parent,false)

            return DetailViewHolder(binding)
        }
    }
}

class DetailClickListener(val clickListener : (album : Album) -> Unit){
    fun onClick(album: Album) = clickListener(album)
}

class DetailDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.albumId == newItem.albumId
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}
