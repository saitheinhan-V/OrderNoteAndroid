package com.note.order.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.note.order.repository.BaseRepository
import com.note.order.request.LoginRequest

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BaseRepository(application)

    suspend fun login(req: LoginRequest) = repository.login(req)
}