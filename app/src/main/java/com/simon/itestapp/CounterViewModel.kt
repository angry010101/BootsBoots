package com.simon.itestapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CounterViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BootRepository(application)

    private val _boots = MutableLiveData<List<BootModel>>(emptyList())
    val boots: LiveData<List<BootModel>> = _boots

    fun requestBoots() =
        viewModelScope.launch(Dispatchers.IO) {
            val boots = repository.getBoots() ?: emptyList()
            withContext(Dispatchers.Main) {
                _boots.value = boots
            }
        }
}