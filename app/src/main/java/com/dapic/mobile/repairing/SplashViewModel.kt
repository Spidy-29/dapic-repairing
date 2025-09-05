package com.dapic.mobile.repairing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SplashViewModel @Inject constructor(
    private val repository: DataRepository
):ViewModel(){

    open fun prefetchData(onComplete:(Boolean) -> Unit){
        viewModelScope.launch {
            try{
                repository.fetchSheetData()
                onComplete(true)
            }catch (e:Exception){
                e.printStackTrace()
                onComplete(false) // Data fetch failed
            }
        }
    }
}