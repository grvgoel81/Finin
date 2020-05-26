package com.example.finin.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserResponse(
    @SerializedName("page") val page: Int?,
    @SerializedName("per_page") val per_page: Int?,
    @SerializedName("total") val total: Int?,
    @SerializedName("total_pages") val total_pages: Int?,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("data") val data: List<Data>?,
    @SerializedName("ad") val ad: Ad
)

@Parcelize
data class Data(
    @SerializedName("id") val id: Int?,
    @SerializedName("email") val email: String?,
    @SerializedName("first_name") val first_name: String?,
    @SerializedName("last_name") val last_name: String?,
    @SerializedName("avatar") val avatar: String?
): Parcelable

data class Ad(
    @SerializedName("company") val company: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("text") val text: String?
)