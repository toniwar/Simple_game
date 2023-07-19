package com.example.simplegame.presentation.views

import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.LayoutParams
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simplegame.databinding.FragmentGameWindowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GameWindow : Fragment() {
    private val player by lazy { PlayerView(requireContext())}
    private lateinit var viewModel: GameWindowViewModel
    private lateinit var params:LayoutParams
    private var pos: Point? = null
    
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        val screenWidth = metrics.widthPixels
        val cellWidth = screenWidth/5
        params = LayoutParams(cellWidth, cellWidth)
        createGameField(params)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GameWindowViewModel::class.java]


        CoroutineScope(Dispatchers.Main).launch {
            viewModel.state.collect {

            }
        }


    }

    override fun onStart() {
        super.onStart()

        binding.root.invalidate()
        if(pos == null) pos = binding.gameGrid[22].getLocation()
        createPlayer(params, pos!!.x, pos!!.y)
        Log.d("CellPos", "${pos!!.x}, ${pos!!.y}")

    }





    private fun createGameField(params: LayoutParams){
        for(i in 0 until 25){
            val cell = GameCellView(requireContext()).apply{
                layoutParams = params
                id = i+100

            }
            binding.gameGrid.addView(cell)
            cell.setOnClickListener {
                pos = it.getLocation()
                movePlayer(pos!!.x, pos!!.y)
            }

        }


    }

    private fun createPlayer(params: LayoutParams, playerPosX: Int, playerPosY: Int){
        binding.root.removeView(player)

        player.apply {
            if(layoutParams == null) layoutParams = params
            binding.root.addView(player)
            translationX = playerPosX.toFloat()
            translationY = playerPosY.toFloat() - 60

        }


    }

    private fun movePlayer(playerPosX: Int, playerPosY: Int){
        player.apply {
            translationX = playerPosX.toFloat()
            translationY = playerPosY.toFloat() - 60
        }
    }

    companion object{
        fun View.getLocation(): Point{
            val location = IntArray(2)
            getLocationInWindow(location)
            return Point(location[0], location[1])
        }
    }

}