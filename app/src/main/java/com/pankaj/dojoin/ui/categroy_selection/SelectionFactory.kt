package com.pankaj.dojoin.ui.categroy_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pankaj.dojoin.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class SelectionFactory(
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SelectionViewModel() as T
    }
}