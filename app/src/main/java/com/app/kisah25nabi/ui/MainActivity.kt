package com.app.kisah25nabi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.app.kisah25nabi.R
import com.app.kisah25nabi.data.KisahResponse
import com.app.kisah25nabi.databinding.ActivityMainBinding
import com.app.kisah25nabi.ui.adapter.KisahAdapter
import com.app.kisah25nabi.ui.detail.DetailActivity
import com.app.kisah25nabi.ui.detail.DetailActivity.Companion.EXTRA_DATA

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

//    private var _viewModel : MainViewModel? = null
//    private val viewModel get() = _viewModel as MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getKisahNabi()

        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.isError.observe(this) { showError(it) }
        viewModel.kisahResponse.observe(this) { showData(it) }
    }

    private fun showLoading(isLoading: Boolean?) {
        if (isLoading == true) {
            binding.progressMain.visibility = View.VISIBLE
        } else {
            binding.progressMain.visibility = View.INVISIBLE
        }
    }

    private fun showData(data: List<KisahResponse>?){
        binding.recyclerMain.apply {
            val mAdapter = KisahAdapter()
            mAdapter.setData(data)
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = mAdapter
            mAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                override fun OnItemClicked(item: KisahResponse) {
                    startActivity(
                        Intent(this@MainActivity, DetailActivity::class.java)
                            .putExtra(EXTRA_DATA, item)
                    )
                }
            })
        }
    }

    private fun showError(isError: Throwable?){
        Log.e("MainActivity", "Error get data $isError" )
    }
}