package com.example.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.favorite.databinding.FragmentFavBinding
import com.example.favorite.di.favModule
import org.koin.core.context.loadKoinModules


class FavFragment : Fragment() {

    private var binding: FragmentFavBinding? = null
    private val binding_ get() = binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding_?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favModule)

        val sectionsPagerAdapter = SectionsPagerAdapter(context as Context, childFragmentManager)
        binding_?.viewPagerFav?.adapter = sectionsPagerAdapter
        binding_?.tabsFav?.setupWithViewPager(binding_?.viewPagerFav)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}