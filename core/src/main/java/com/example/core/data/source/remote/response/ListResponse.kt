package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T> (
        @field:SerializedName("results")
        val results: List<T>? = null
)