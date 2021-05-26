package com.example.sub1capstonerezi.tv

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.core.data.Resource
import com.example.core.domain.model.DataMT
import com.example.core.ui.DataMTAdapter
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import com.example.sub1capstonerezi.R
import com.example.sub1capstonerezi.databinding.FragmentTvBinding
import com.example.sub1capstonerezi.detail.DetailActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.sub1capstonerezi.home.HomeActivity
import com.example.sub1capstonerezi.home.SearchViewModel
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@FlowPreview
class TvFragment : Fragment(), View.OnClickListener {
    private var binding: FragmentTvBinding? = null
    private val binding_ get() = binding
    private val vModel: TvViewModel by viewModel()
    private lateinit var adapterDataMT: DataMTAdapter
    private val searchModel: SearchViewModel by viewModel()
    private lateinit var searchMaterial: MaterialSearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTvBinding.inflate(inflater, container, false)
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

        with(binding_?.rvTv) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = adapterDataMT
        }

        adapterDataMT.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        binding_?.rvTv?.setOnClickListener (this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val item = menu.findItem(R.id.search)
        searchMaterial.setMenuItem(item)
    }

    private fun setList() {
        vModel.getTv().observe(viewLifecycleOwner, tvObserver)
    }

    private val tvObserver = Observer<Resource<List<DataMT>>> { Tv ->
        if (Tv != null) {
            when (Tv) {
                is Resource.Loading -> {
                    setVisibility(true,false)
                }
                is Resource.Success -> {
                    setVisibility(false,false)
                    adapterDataMT.setData(Tv.data)
                }
                is Resource.Error -> {
                    setVisibility(false,true)
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
        searchModel.tvResult.observe(viewLifecycleOwner, { Tv ->
            if (Tv.isNullOrEmpty()){
                setVisibility(false,true)
            } else {
                setVisibility(false,false)
            }
            adapterDataMT.setData(Tv)
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
            binding_?.progressbarTv?.visibility = View.VISIBLE
        }
        else{
            binding_?.progressbarTv?.visibility = View.GONE
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
        binding_?.rvTv?.adapter = null
        binding = null
    }

    override fun onClick(v: View?) {
        when (v) {
            binding_?.rvTv ->{
                setList()
            }
        }
    }
}