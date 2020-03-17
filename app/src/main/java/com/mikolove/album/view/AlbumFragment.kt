package com.mikolove.album.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mikolove.album.R
import com.mikolove.album.data.AlbumDatabase
import com.mikolove.album.databinding.FragmentAlbumBinding
import timber.log.Timber

class AlbumFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding : FragmentAlbumBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_album,container,false)

        val application = requireNotNull(this.activity).application

        val database = AlbumDatabase.getInstance(application)

        val viewModelFactory = AlbumViewModelFactory(database)

        val viewModel = ViewModelProvider(this,viewModelFactory).get(AlbumViewModel::class.java)

        binding.lifecycleOwner = this
        binding.albumViewModel = viewModel

        val adapter = AlbumAdapter( AlbumItemClickListener { it ->
            it?.let {
                this.findNavController().navigate(AlbumFragmentDirections.actionAlbumFragmentToDetailFragment(it.id))
            }
        })

        val layoutManager= GridLayoutManager(application, 5)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    0 -> 5
                    else -> 1
                }
            }
        }

        binding.albumRecyclerView.layoutManager = layoutManager
        binding.albumRecyclerView.adapter = adapter

        viewModel.allAlbum.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }
}