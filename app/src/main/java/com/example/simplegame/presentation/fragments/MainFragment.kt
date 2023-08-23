package com.example.simplegame.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simplegame.databinding.FragmentMainBinding
import com.example.simplegame.presentation.view_models.MainViewModel


class MainFragment : Fragment() {
    private lateinit var mainVM: MainViewModel
    private val binding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainVM = ViewModelProvider(requireActivity())[MainViewModel::class.java]

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newGameButton.setOnClickListener{
            mainVM.sendData("Game")
        }
    }


}