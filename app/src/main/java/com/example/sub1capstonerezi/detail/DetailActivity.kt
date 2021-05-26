package com.example.sub1capstonerezi.detail

import android.content.Context
import com.bumptech.glide.Glide
import android.os.Bundle
import com.example.core.utils.Network.IMAGE_URL
import com.example.sub1capstonerezi.R
import com.example.sub1capstonerezi.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel
import com.google.android.material.snackbar.Snackbar
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.example.core.domain.model.DataMT

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "data"
    }
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    private var title: String = "Detail"
    private fun setActionBarTitle(title: String, state: Boolean) {
        if (state){
            supportActionBar?.title = title + " Tv"
        }
        else{
            supportActionBar?.title = title + " Movie"
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailMovie = intent.getParcelableExtra<DataMT>(EXTRA_DATA)
        if (detailMovie != null) {
            dataDisplayDetail(detailMovie)
        }
        detailMovie?.let { setActionBarTitle(title, it.mt) }

    }
    private fun dataDisplayDetail(data: DataMT) {
        with(binding) {
            data.poster?.let { setImage(this@DetailActivity, it, adImgItem) }
            data.poster?.let { setImage(this@DetailActivity, it, adImgView) }
            val date = data.date
            if (date != null) {
                adTitle.text = data.title + " ("+ date.subSequence(0,4) + ")"
            }
            adRating.text = data.score.toString()
            adDate.text = data.date
            adOverview.text = data.overview
            adOver.text = getString(R.string.overview_mt)
            data.favorite.let { stat ->
                checkFav(stat)
            }

            binding.udToggleFavorite.setOnClickListener {
                crud(data)
            }
            binding.adShare.setOnClickListener { share() }
        }
    }
    private fun crud(data: DataMT) {
        if (data.favorite){
            Snackbar.make(findViewById(android.R.id.content), "${data.title} Removed from favorite", Snackbar.LENGTH_SHORT).show()
            viewModel.setDataMTFav(data, false)
            data.favorite = false
        }else {
            Snackbar.make(findViewById(android.R.id.content), "${data.title} Added to favorite", Snackbar.LENGTH_SHORT).show()
            viewModel.setDataMTFav(data, true)
            data.favorite = true
        }

    }

    private fun setImage(context: Context, imagePath: String, imageView: ImageView) {
        Glide.with(context).clear(imageView)
        Glide.with(context).load(IMAGE_URL+imagePath).into(imageView)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun checkFav(state: Boolean){
        if (state) {
            setUserFav(true)
        } else {
            setUserFav(false)
        }
    }
    private fun setUserFav(state: Boolean){
        binding.udToggleFavorite.isChecked = state
    }
    private fun share() {
        ShareCompat.IntentBuilder.from(this).apply {
            setType("text/plain")
            setChooserTitle(getString(R.string.titleShare))
            setText(getString(R.string.bodyShare1)+" "+binding.adTitle.text.toString()+" "+getString(R.string.bodyShare2))
            startChooser()
        }
    }
}