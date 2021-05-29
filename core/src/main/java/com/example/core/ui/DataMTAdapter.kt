package com.example.core.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View.GONE
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.core.databinding.ItemRowMtBinding
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.core.domain.model.DataMT
import com.example.core.utils.Network.IMAGE_URL
import com.example.core.utils.DiffUtils
import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource

@Suppress("DEPRECATION")
class DataMTAdapter : RecyclerView.Adapter<DataMTAdapter.DataMTViewHolder>() {

    internal var listData = ArrayList<DataMT>()
    var onItemClick: ((DataMT) -> Unit)? = null

    fun setData(listDataMT: List<DataMT>?) {
        if (listDataMT == null) return
        val diffUtilCallback = DiffUtils(listData, listDataMT)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        listData.clear()
        listData.addAll(listDataMT)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getSwipedData(swipedPosition: Int): DataMT {
        return listData[swipedPosition]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataMTViewHolder {
        val itemRowMtBinding = ItemRowMtBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataMTViewHolder(itemRowMtBinding)
    }

    override fun onBindViewHolder(holder: DataMTViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class DataMTViewHolder(private val binding: ItemRowMtBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataMT: DataMT) {
            with(binding) {
                val date = dataMT.date
                if (date != null) {
                    irTitle.text = dataMT.title + " ("+ date.subSequence(0,4) + ")"
                }
                irRating.text = dataMT.score.toString()
                irDate.text = dataMT.date

                Glide.with(itemView.context)
                        .load(IMAGE_URL + dataMT.poster)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    isFirstResource: Boolean
                            ): Boolean {
                                progressbarRow.visibility = GONE
                                return false
                            }

                            override fun onResourceReady(
                                    resource: Drawable?,
                                    model: Any?,
                                    target: Target<Drawable>?,
                                    dataSource: DataSource?,
                                    isFirstResource: Boolean
                            ): Boolean {
                                progressbarRow.visibility = GONE
                                return false
                            }
                        })
                        .into(irImgItem)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[absoluteAdapterPosition])
            }
        }
    }
}