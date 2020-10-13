package com.pankaj.dojoin.ui.category

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.pankaj.dojoin.apiclient.ApiUtils
import com.pankaj.dojoin.repository.AppRepository
import com.pankaj.dojoin.utils.Coroutines
import com.pankaj.dojoin.utils.ProgressDialogUtil



class CategoryViewModel(val repository: AppRepository) : ViewModel() {

    /**
     * Variable declaration
     */
    var dataFound = MutableLiveData<Boolean>()



    /**
     * Initilize default value
     */
    init {
        dataFound.value = false

    }

    /**
     * Api call for category list
     */
    fun callApiCategory(mActivity: Activity) {

        ProgressDialogUtil.showProgress(mActivity)
        val request = JsonObject()

     //   ApiParms(mActivity).addParam(request)

        Coroutines.main {

            try {
                val response = repository.getCategory()

                response.result
                ProgressDialogUtil.hideProgress()


                (mActivity as CategoryActivity).setAdapter(response.result)

            } catch (e: Exception) {
                ProgressDialogUtil.hideProgress()
                ApiUtils.handleRetrofitError(e)


            }
        }


    }
}