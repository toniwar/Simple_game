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


class LoseFragment : Fragment() {

    private lateinit var mainVM: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainVM = ViewModelProvider(requireActivity())[MainViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onBackClick = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                mainVM.sendData("Счёт: ${mainVM.score}")
                mainVM.sendData("Home")
            }
        }
        (requireActivity()).onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackClick)
    }


}