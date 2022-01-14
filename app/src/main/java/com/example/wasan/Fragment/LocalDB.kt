package com.example.wasan.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wasan.R
import com.example.wasan.RecyclerView.LocalAdapter
import com.example.wasan.ShowsViewModel
import kotlinx.android.synthetic.main.fragment_local_d_b.view.*


class LocalDB : Fragment() {

    private lateinit var viewModel: ShowsViewModel
    private lateinit var localAdapter: LocalAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_local_d_b, container, false)

        viewModel = ViewModelProvider(this).get(ShowsViewModel::class.java)
        viewModel.getTVShow().observe(viewLifecycleOwner, {
                tvShows -> localAdapter.update(tvShows)
        })

        localAdapter = LocalAdapter(viewModel)
        view.rvLocal.adapter = localAdapter
        view.rvLocal.layoutManager = LinearLayoutManager(activity)
        return view
    }

}