package com.adriandp.marvel.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adriandp.marvel.BR
import com.adriandp.marvel.R
import com.adriandp.marvel.databinding.ActivityMainBinding
import com.adriandp.marvel.view.viewmodel.MainActivityVM


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setVariable(BR.model, MainActivityVM(binding.root.context))
        binding.executePendingBindings()

        binding.rvComic.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                if (binding.model?.loadingItems == false) {
                    val elementOnList = recyclerView.adapter?.itemCount?.minus(1)
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == elementOnList) {
                        Log.d(this::class.java.simpleName, "loadMoreitemsCall")
                        if (binding.model?.total ?: 0 > elementOnList) {
                            binding.model?.loadMoreItems()
                        } else {
                            Toast.makeText(
                                binding.root.context,
                                "No more",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        })

        (binding.rvComic.layoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if(position == 0) 4 else 1
                }

            }


    }
}
