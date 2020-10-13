package com.pankaj.dojoin.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pankaj.dojoin.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class CategoryFactory(
    private val repository: AppRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryViewModel(repository) as T
    }
}