package com.example.simplegame.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import com.example.simplegame.R
import com.example.simplegame.presentation.view_models.MainViewModel


class VictoryFragment : Fragment() {
    private lateinit var mainVM: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainVM = ViewModelProvider(requireActivity())[MainViewModel::class.java]


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_victory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainVM.score++
        mainVM.sendData("Счёт: ${mainVM.score}")
        val onBackClick = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {

                mainVM.sendData("Home")
            }
        }
        (requireActivity()).onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackClick)
    }


}