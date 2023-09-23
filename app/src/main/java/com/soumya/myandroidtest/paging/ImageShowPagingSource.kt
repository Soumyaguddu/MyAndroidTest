package com.soumya.myandroidtest.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.soumya.myandroidtest.apiService.ApiService
import com.soumya.myandroidtest.model.ImageShowData
import retrofit2.HttpException
import java.io.IOException

class ImageShowPagingSource
constructor(private val apiService: ApiService) : PagingSource<Int,ImageShowData>() {

    private val DEFAULT_PAGE_INDEX= 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageShowData> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = apiService.getAllImages(page,params.loadSize)
            LoadResult.Page(
                response,
                prevKey = if(page == DEFAULT_PAGE_INDEX) null else page-1,
                nextKey = if(response.isEmpty()) null else page+1
            )
        } catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ImageShowData>): Int? {
        return null
    }
}