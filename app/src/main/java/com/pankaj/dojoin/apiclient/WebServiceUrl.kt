package com.pankaj.dojoin.apiclient

/**
 * Created by pankaj jangid on 2/12/2018.
 */

object WebServiceUrl {

    enum class ENVIRONMENT {
        LOCAL,
        DEVELOPMENT,
        QA,
        UAT,
    }


    val APP_ENVIROMENT = ENVIRONMENT.UAT.ordinal
    //Dev server
    const val BASE_URL_LOCAL = ""
    const val BASE_URL_DEVELOPMENT = ""
    const val BASE_URL_QA = "http://208.109.13.111:9090/api/"
    const val BASE_URL_UAT = "http://208.109.13.111:9090/api/"



    const val HIDE_DEBUG_LOGS = false
    const val STATUS_SUCCESS = 200 //Success

    const val STATUS_AUTH_FAILED =
        402 // This error occurs when either "api_access_key" in headers is missing or incorrect.
    const val STATUS_PARAMTER_MISSING =
        403  // This error occurs when any required parameter is missing in request params.
    const val STATUS_SESSION_EXPIRE =
        401

    //This error occurs when login user session has been expired. On this error, call "user_logout" api .
    const val STATUS_SERVER_ERROR =
        500  //This error occurs when node server crashed due to any of reason.
    const val STATUS_SERVER_400 =
        400  //This error occurs when node server crashed due to any of reason.


    /*
        Api Endpoints
     */
    const val _CATEGORYS = "Category"



    fun getBaseUrl(): String {

        var baseUrl = ""
        when (APP_ENVIROMENT) {
            ENVIRONMENT.LOCAL.ordinal -> {
                baseUrl = BASE_URL_LOCAL
            }
            ENVIRONMENT.DEVELOPMENT.ordinal -> {
                baseUrl = BASE_URL_DEVELOPMENT
            }
            ENVIRONMENT.QA.ordinal -> {
                baseUrl = BASE_URL_QA
            }
            ENVIRONMENT.UAT.ordinal -> {
                baseUrl = BASE_URL_UAT
            }
            else -> {
                baseUrl = BASE_URL_DEVELOPMENT
            }
        }


        return baseUrl

    }

}



