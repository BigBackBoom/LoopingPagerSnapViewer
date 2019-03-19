package com.bigbackboom.loopingpagersnapviewer

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import com.bigbackboom.loopingpagersnapviewer.databinding.ActivityMainBinding
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: SampleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        adapter = SampleAdapter()
        binding.recyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(this@MainActivity).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            layoutManager = linearLayoutManager

            val scrollListener = SampleAdapter.OnScrollListener(linearLayoutManager)
            addOnScrollListener(scrollListener)

            val helper = PagerSnapHelper()
            helper.attachToRecyclerView(this)

            adapter = this@MainActivity.adapter
        }

    }
}
