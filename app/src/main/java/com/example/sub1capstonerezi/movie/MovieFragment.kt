package com.example.sub1capstonerezi.movie

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Resource
import com.example.core.domain.model.DataMT
import com.example.core.ui.DataMTAdapter
import com.example.sub1capstonerezi.R
import com.example.sub1capstonerezi.databinding.FragmentMovieBinding
import com.example.sub1capstonerezi.detail.DetailActivity
import com.example.sub1capstonerezi.home.HomeActivity
import com.example.sub1capstonerezi.home.SearchViewModel
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class MovieFragment : Fragment(), View.OnClickListener {
    private var binding: FragmentMovieBinding? = null
    private val binding_ get() = binding
    private val vModel: MovieViewModel by viewModel()
    private lateinit var adapterDataMT: DataMTAdapter
    private val searchModel: SearchViewModel by viewModel()
    private lateinit var searchMaterial: MaterialSearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        val tBar: Toolbar = activity?.findViewById<View>(R.id.tBar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(tBar)
        setHasOptionsMenu(true)
        searchMaterial = (activity as HomeActivity).findViewById(R.id.search_view)
        return binding_?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterDataMT = DataMTAdapter()
        setList()
        observeSearchQuery()
        setSearchList()

        with(binding_?.rvMovie) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = adapterDataMT
        }

        adapterDataMT.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        binding_?.rvMovie?.setOnClickListener (this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val item = menu.findItem(R.id.search)
        searchMaterial.setMenuItem(item)
    }

    private fun setList() {
        vModel.getMovie().observe(viewLifecycleOwner, movieObserver)
    }

    private val movieObserver = Observer<Resource<List<DataMT>>> { Movie ->
        if (Movie != null) {
            when (Movie) {
                is Resource.Loading -> {
                    setVisibility(true,false)
                }
                is Resource.Success -> {
                    setVisibility(false,false)
                    adapterDataMT.setData(Movie.data)
                }
                is Resource.Error -> {
                    setVisibility(false,false)
                    Toast.makeText(context, "there is an error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeSearchQuery() {
        searchMaterial.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchModel.setSearchQuery(it)
                }
                return true
            }
        })
    }

    private fun setSearchList() {
        searchModel.movieResult.observe(viewLifecycleOwner, { Movie ->
            if (Movie.isNullOrEmpty()){
                setVisibility(false,true)
            } else {
                setVisibility(false,false)
            }
            adapterDataMT.setData(Movie)
        })
        searchMaterial.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener{
            override fun onSearchViewShown() {
                setVisibility(false,false)
            }
            override fun onSearchViewClosed() {
                setVisibility(false,false)
                setList()
            }
        })
    }

    private fun setVisibility(stateProgressBar: Boolean, stateNoData: Boolean){
        if (stateProgressBar){
            binding_?.progressbarMovie?.visibility = View.VISIBLE
        }
        else{
            binding_?.progressbarMovie?.visibility = View.GONE
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
        searchMaterial.setOnQueryTextListener(null)
        searchMaterial.setOnSearchViewListener(null)
        binding_?.rvMovie?.adapter = null
        binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding_?.rvMovie ->{
                setList()
            }
        }
    }
}