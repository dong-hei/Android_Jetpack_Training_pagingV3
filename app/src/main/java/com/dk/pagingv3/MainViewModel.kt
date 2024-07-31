package com.dk.pagingv3

import MyPagingSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dk.pagingv3.data.GithubItems
import com.dk.pagingv3.network.GithubApi
import com.dk.pagingv3.network.RetrofitInstance
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel(){

    private val api = RetrofitInstance.getInstance().create(GithubApi::class.java)

    val items : Flow<PagingData<GithubItems>> = Pager(
        config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            MyPagingSource(api)
        }
    )
        .flow
        .cachedIn(viewModelScope)

}