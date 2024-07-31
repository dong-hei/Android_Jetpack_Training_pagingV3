package com.dk.pagingv3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val rv = findViewById<RecyclerView>(R.id.rv)
        val myAdapter = MyAdapter()

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = myAdapter

        lifecycleScope.launch {

            viewModel.items.collect{

                myAdapter.submitData(it)

            }

        }

        val loadingNext = findViewById<TextView>(R.id.loadingNext)
        val loadingPrev = findViewById<TextView>(R.id.loadingPrev)

        lifecycleScope.launch {
            myAdapter.loadStateFlow.collect {

                val isLoadingNext = it.source.append is LoadState.Loading
                loadingNext.isVisible = isLoadingNext

                val isLoadingPrev = it.source.prepend is LoadState.Loading
                loadingPrev.isVisible = isLoadingPrev

            }
        }

        val refresh = findViewById<Button>(R.id.refreshBtn)
        refresh.setOnClickListener {
            myAdapter.refresh()
        }

    }
}