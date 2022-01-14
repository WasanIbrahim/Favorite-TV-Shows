package com.example.wasan.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.wasan.R

class NewMain : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_new_main, container, false)

        view.findViewById<TextView>(R.id.tvApi).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_newMain_to_browseAPI)
        }

        view.findViewById<TextView>(R.id.tvLocal).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_newMain_to_localDB)
        }

        return view
    }


}