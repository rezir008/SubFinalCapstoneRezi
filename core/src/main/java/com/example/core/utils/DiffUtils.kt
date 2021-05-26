package com.extcode.project.core.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.core.domain.model.DataMT

class DiffUtils(private val oldData: List<DataMT>, private val newData: List<DataMT>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].id == newData[newItemPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (id,
            title,
            overview,
            score,
            date,
            poster,
            favorite,
            mt) = oldData[oldPosition]
        val (idNew,
            titleNew,
            overviewNew,
            scoreNew,
            dateNew,
            posterNew,
            favoriteNew,
            mtNew) = newData[newPosition]

        return id == idNew &&
        title == titleNew &&
        overview == overviewNew &&
        score == scoreNew &&
        date == dateNew &&
        poster == posterNew &&
        favorite == favoriteNew &&
        mt == mtNew
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}