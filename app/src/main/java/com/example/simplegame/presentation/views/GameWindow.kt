package com.example.simplegame.presentation.views

import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.OrientationHelper.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.example.simplegame.R
import com.example.simplegame.databinding.FragmentGameWindowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GameWindow : Fragment() {
    private lateinit var viewModel: GameWindowViewModel
    
    private val binding by lazy {
        FragmentGameWindowBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GameWindowViewModel::class.java]
        val adapter = GameFieldAdapter()
        binding.gameGrid.layoutManager = GridLayoutManager(requireContext(),5, RecyclerView.HORIZONTAL, false)
        binding.gameGrid.adapter = adapter
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.state.collect {
                adapter.initGameField(it)
                adapter.notifyDataSetChanged()
            }
        }
    }



}