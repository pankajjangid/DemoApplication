package com.pankaj.dojoin.network_response
import com.google.gson.annotations.SerializedName


data class CategoryResponse(
    @SerializedName("errorMessage")
    val errorMessage: Any,
    @SerializedName("paging")
    val paging: Any,
    @SerializedName("result")
    val result: List<Result>,
    @SerializedName("serverTime")
    val serverTime: String
) {
    data class Result(
        @SerializedName("id")
        val id: String,
        @SerializedName("subCategories")
        val subCategories: List<SubCategory>,
        @SerializedName("title")
        val title: String
    ) {
        data class SubCategory(
            @SerializedName("id")
            val id: String,
            @SerializedName("title")
            val title: String
        )
    }
}