package com.example.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.DataMT
import com.example.core.ui.DataMTAdapter
import com.example.favorite.FavViewModel
import com.example.favorite.databinding.FragmentMovieFavBinding
import com.example.sub1capstonerezi.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFavFragment : Fragment() {

    private var binding: FragmentMovieFavBinding? = null
    private val binding_ get() = binding
    internal val vModel: FavViewModel by viewModel()
    internal lateinit var adapterDataMT: DataMTAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieFavBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        return binding_?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding_?.rvMovieFav)
        setVisibility(true, false)

        adapterDataMT = DataMTAdapter()
        setList()

        with(binding_?.rvMovieFav) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = adapterDataMT
        }

        adapterDataMT.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        binding_?.rvMovieFav?.setOnClickListener {
            setList()
        }
    }
    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.absoluteAdapterPosition
                val dataMT = adapterDataMT.getSwipedData(swipedPosition)
                vModel.setFavorite(dataMT, !dataMT.favorite)
            }
        }
    })

    private fun setList() {
        vModel.getMovieFav().observe(viewLifecycleOwner, movieFavObserver)
    }

    private val movieFavObserver = Observer<List<DataMT>> { movieFav ->
        if (movieFav.isNullOrEmpty()) {
            setVisibility(false, true)
            }
        else{
            setVisibility(false, false)
        }
        adapterDataMT.setData(movieFav)
    }

    private fun setVisibility(stateProgressBar: Boolean, stateNoData: Boolean){
        if (stateProgressBar){
            binding_?.progressbarMovieFav?.visibility = View.VISIBLE
        }
        else{
            binding_?.progressbarMovieFav?.visibility = View.GONE
        }
        if (stateNoData){
            binding_?.noData?.visibility = View.VISIBLE
            binding_?.noDataText?.visibility = View.VISIBLE
        }
        else{
            binding_?.noData?.visibility = View.GONE
            binding_?.noDataText?.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding_?.rvMovieFav?.adapter = null
        binding = null
    }
}