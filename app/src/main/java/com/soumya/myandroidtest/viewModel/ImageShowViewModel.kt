package com.soumya.myandroidtest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.soumya.myandroidtest.apiService.ApiService
import com.soumya.myandroidtest.model.ImageShowData
import com.soumya.myandroidtest.paging.ImageShowPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ImageShowViewModel@Inject
constructor(
    private val apiService: ApiService
) : ViewModel() {

    val getAllPagingImages: Flow<PagingData<ImageShowData>> = Pager(config = PagingConfig(20,enablePlaceholders = false)){
        ImageShowPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)

}