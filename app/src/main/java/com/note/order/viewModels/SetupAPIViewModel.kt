package com.note.order.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.note.order.repository.APIDataRepository


class SetupAPIViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = APIDataRepository(application)

    fun getAllBrand() = repository.getAllBrand()
}