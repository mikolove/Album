package com.mikolove.album.view

import android.os.Bundle
import android.view.*
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

    private lateinit var binding : FragmentAlbumBinding
    private lateinit var viewModel : AlbumViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album,container,false)

        val application = requireNotNull(this.activity).application

        val database = AlbumDatabase.getInstance(application)

        val viewModelFactory = AlbumViewModelFactory(database)

        viewModel = ViewModelProvider(this,viewModelFactory).get(AlbumViewModel::class.java)

        binding.lifecycleOwner = this
        binding.albumViewModel = viewModel

        setHasOptionsMenu(true)

        val adapter = AlbumAdapter( AlbumItemClickListener { it ->
            it?.let {
                this.findNavController().navigate(AlbumFragmentDirections.actionAlbumFragmentToDetailFragment(it.id))
            }
        })

        val manager = GridLayoutManager(application, AlbumAdapter.ITEM_GRID_NUMBER)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    AlbumAdapter.ITEM_VIEW_TYPE_HEADER -> AlbumAdapter.ITEM_GRID_NUMBER
                    else -> AlbumAdapter.ITEM_GRID_MIN_NUMBER
                }
            }
        }

        binding.albumRecyclerView.layoutManager = manager
        binding.albumRecyclerView.adapter = adapter

        viewModel.allAlbum.observe(viewLifecycleOwner, Observer {
            Timber.i("List size %d",it.size)
            if(it.isEmpty()){
                hideData()
            }else{
                adapter.submitList(it)
                showData()
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if(it)
                showProgress()
            else
                hideProgress()
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_album_refresh -> {
                if(!viewModel.getLoadingState()!!)
                    viewModel.refreshAlbum()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun showData(){
        binding.albumError.visibility= View.GONE
        binding.albumRecyclerView.visibility = View.VISIBLE
    }
    fun hideData(){
        binding.albumRecyclerView.visibility = View.GONE
        binding.albumError.visibility= View.VISIBLE
    }

    fun showProgress(){
        binding.albumError.visibility= View.GONE
        binding.albumRecyclerView.visibility = View.GONE
        binding.albumProgress.visibility = View.VISIBLE
    }

    fun hideProgress(){
        binding.albumProgress.visibility = View.GONE
        binding.albumRecyclerView.visibility = View.VISIBLE
    }


}