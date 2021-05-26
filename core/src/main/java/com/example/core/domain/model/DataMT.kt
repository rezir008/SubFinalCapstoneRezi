package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataMT (
    var id: Int? = null,
    var title: String? = null,
    var overview: String? = null,
    var score: Double = 0.0,
    var date: String? = null,
    var poster: String? = null,
    var favorite: Boolean = false,
    var mt: Boolean = false
) : Parcelable