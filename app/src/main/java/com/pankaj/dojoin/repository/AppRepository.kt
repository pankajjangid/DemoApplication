package com.pankaj.dojoin.repository

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.pankaj.dojoin.apiclient.MyApi
import com.pankaj.dojoin.apiclient.SafeApiRequest
import com.pankaj.dojoin.network_response.CategoryResponse


class AppRepository(val myApi: MyApi) : SafeApiRequest() {


    suspend fun getCategory(): CategoryResponse {
        return apiRequest { myApi._getCategorys() }
    }


}