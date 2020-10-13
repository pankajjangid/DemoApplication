package com.pankaj.dojoin.apiclient

import android.app.Activity
import com.pankaj.dojoin.utils.MyApplication
import com.pankaj.dojoin.utils.Utils
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ApiUtils {


    fun handleRetrofitError(e: Throwable) {
        e.printStackTrace()

        if (e is HttpException) {
            val responseBody: ResponseBody = e.response()!!.errorBody()!!
            Utils.showToast(MyApplication.application!!, getErrorMessage(responseBody))
        } else if (e is SocketTimeoutException) {

            Utils.showToast(MyApplication.application!!, "Response time out,please try again")
        } else if (e is IOException) {
            Utils.showToast(MyApplication.application!!, "Error occurred ,please try again")

        } else {
            Utils.showToast(MyApplication.application!!, "Unknown error ,please try again")

        }
    }

    fun getErrorMessage(responseBody: ResponseBody): String {
        try {
            val jsonObject = JSONObject(responseBody.string())
            return jsonObject.getString("message")
        } catch (e: Exception) {
            return e.message!!
        }
    }

    fun checkResopnseStatus(
        mActivity: Activity,
        status: Int,
        message: String,
        showSuccessMessage: Boolean
    ): Boolean {

        when (status) {
            WebServiceUrl.STATUS_SUCCESS -> {
                if (showSuccessMessage)
                    Utils.showToast(mActivity, message)
                return true
            }
            WebServiceUrl.STATUS_AUTH_FAILED -> {
                //handleAuthFaild(mActivity, message)
                return false
            }
            WebServiceUrl.STATUS_PARAMTER_MISSING -> {
                handleServerError(mActivity, message)
                return false
            }
            WebServiceUrl.STATUS_SESSION_EXPIRE -> {
                handleAuthFaild(mActivity, message)
                return false
            }
            WebServiceUrl.STATUS_SERVER_ERROR -> {
                handleServerError(mActivity, message)
                return false
            }
            WebServiceUrl.STATUS_SERVER_400 -> {
                handleServerError(mActivity, message)
                return false
            }
            else -> return false

        }

    }

    private fun handleServerError(mActivity: Activity, message: String) {
        Utils.showToastLong(mActivity, message)

    }



    private fun handleAuthFaild(activity: Activity, msg: String) {
        if (msg.isNotEmpty())
            Utils.showToast(activity, msg)

    //    (activity as HomeActivity).mViewModel.callApi_UserLogout(activity)


    }




}