package com.mikolove.album.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mikolove.album.R
import com.mikolove.album.data.AlbumDatabase
import com.mikolove.album.databinding.FragmentDetailBinding
import timber.log.Timber

class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding : FragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail,container,false)

        val application = requireNotNull(this.activity).application

        val database = AlbumDatabase.getInstance(application)

        val arguments = DetailFragmentArgs.fromBundle(arguments!!)

        val id = arguments.id

        val viewModelFactory = DetailViewModelFactory(database,id)

        val viewModel = ViewModelProvider(this,viewModelFactory).get(DetailViewModel::class.java)

        binding.lifecycleOwner = this
        binding.detailViewModel = viewModel

        return binding.root

    }
}