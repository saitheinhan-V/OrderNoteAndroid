package com.note.order.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.note.order.entity.BrandItem
import com.note.order.entity.ColorItem
import com.note.order.repository.DataRepository
import kotlinx.coroutines.launch

class SetupViewModel(private val repository: DataRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allColors: LiveData<List<ColorItem>> = repository.allColors

    val allBrands: LiveData<List<BrandItem>> = repository.allBrands

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertColor(item: ColorItem) = viewModelScope.launch {
        repository.insertColor(item)
    }

    fun deleteColor(item: ColorItem) = viewModelScope.launch {
        repository.deleteColor(item)
    }

    fun updateColor(item: ColorItem) = viewModelScope.launch {
        repository.updateColor(item)
    }

    fun insertBrand(item: BrandItem) = viewModelScope.launch {
        repository.insertBrand(item)
    }

    fun deleteBrand(item: BrandItem) = viewModelScope.launch {
        repository.deleteBrand(item)
    }

    fun updateBrand(item: BrandItem) = viewModelScope.launch {
        repository.updateBrand(item)
    }


}

class SetupViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SetupViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}