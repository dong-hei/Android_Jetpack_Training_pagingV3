package com.dk.pagingv3.data

data class GithubResponse (
    val total_count : Int,
    val incomplete_results : Boolean,
    val items : List<GithubItems>
)