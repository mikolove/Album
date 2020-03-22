package com.mikolove.album.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mikolove.album.data.entities.AlbumItem
import com.mikolove.album.data.entities.HeaderItem
import com.mikolove.album.data.entities.Item
import com.mikolove.album.databinding.ItemAlbumBinding
import com.mikolove.album.databinding.ItemHeaderBinding

class AlbumAdapter(private val clickListener: AlbumItemClickListener) : ListAdapter<Item, RecyclerView.ViewHolder>(AlbumDiffCallback()) {

    companion object{
        const val ITEM_GRID_MIN_NUMBER = 1
        const val ITEM_GRID_NUMBER = 5
        const val ITEM_VIEW_TYPE_HEADER = 0
        const val ITEM_VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderItemViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> AlbumItemViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlbumItemViewHolder -> {
                val albumItem = getItem(position) as AlbumItem
                holder.bind(clickListener,albumItem)
            }
            is HeaderItemViewHolder-> {
                val headerItem = getItem(position) as HeaderItem
                holder.bind(headerItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HeaderItem -> ITEM_VIEW_TYPE_HEADER
            is AlbumItem -> ITEM_VIEW_TYPE_ITEM
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }
}

class HeaderItemViewHolder(private val binding : ItemHeaderBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HeaderItem) {
        binding.header = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): HeaderItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemHeaderBinding.inflate(layoutInflater,parent,false)

            return HeaderItemViewHolder(binding)
        }
    }
}

class AlbumItemViewHolder(private val binding : ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(clickListener: AlbumItemClickListener, item: AlbumItem) {
        binding.album = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object{
        fun from(parent : ViewGroup) : AlbumItemViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemAlbumBinding.inflate(layoutInflater,parent,false)

            return AlbumItemViewHolder(binding)
        }
    }
}

class AlbumItemClickListener(val clickListener : (album : AlbumItem) -> Unit){
    fun onClick(album: AlbumItem) = clickListener(album)
}

class AlbumDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.isHeader == newItem.isHeader
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
