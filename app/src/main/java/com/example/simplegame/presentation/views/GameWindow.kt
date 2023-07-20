package com.example.simplegame.presentation.views

import android.app.Activity
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
import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.models.Player
import com.example.simplegame.domain.use_cases.MovePlayerUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameWindow : Fragment() {
    private val playerView by lazy { PlayerView(requireContext())}
    private lateinit var player: Player
    private lateinit var enemy: Enemy
    private lateinit var viewModel: GameWindowViewModel
    private lateinit var params:LayoutParams

    
    private val binding by lazy {
        FragmentGameWindowBinding.inflate(layoutInflater)
    }
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val cells = mutableListOf<GameCell>()
    private lateinit var activity: Activity
    private val movePlayerUseCase by lazy { MovePlayerUseCase() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = requireActivity()
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val screenWidth = metrics.widthPixels
        val cellWidth = screenWidth/5
        params = LayoutParams(cellWidth, cellWidth)
        viewModel = ViewModelProvider(requireActivity())[GameWindowViewModel::class.java]


        mainScope.launch{
            viewModel.state.collect { list ->
                list.forEach {
                    cells.add(it)
                }
                Log.d("Cells", cells.size.toString())
                createGameField(params)

            }

        }


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        mainScope.launch {
            viewModel.gameUnitState.collect{
                it?.let {
                    when (it) {
                        is Player -> {
                            player = it
                            setPlayerState()
                        }
                        is Enemy -> enemy = it
                    }

                }

            }
        }

    }

    private suspend fun setPlayerState() {
        if(player.location == null){
            delay(50)
            player.location = getStartLocation()
        }

        val pos = player.location
        createPlayerView(params, pos!!.x, pos.y -60)

    }

    private fun getStartLocation(): Point{
        return binding.gameGrid[22].getLocation()
    }

    override fun onStop() {
        super.onStop()
        mainScope.cancel()
    }


    private fun createGameField(params: LayoutParams){
        for(i in cells.indices){
            val cell = GameCellView(activity).apply{
                layoutParams = params
                id = i+1

            }
            binding.gameGrid.addView(cell)
            cell.setOnClickListener {
                val clickLocation = it.getLocation()
                val accessStep =movePlayerUseCase.move(player,
                    player.location!!.x,
                    player.location!!.y,
                    clickLocation.x,
                    clickLocation.y,
                    player.position,
                    it.id
                )
                if(accessStep){
                    player.location = clickLocation
                    player.position = it.id
                    movePlayerView(player.location!!.x, player.location!!.y - 60)
                    viewModel.setGameUnitState(player)
                }

            }

        }


    }

    private fun createPlayerView(params: LayoutParams, playerPosX: Int, playerPosY: Int){
        binding.root.removeView(playerView)
        playerView.apply {
            if(layoutParams == null) layoutParams = params
            binding.root.addView(playerView)
            translationX = playerPosX.toFloat()
            translationY = playerPosY.toFloat()

        }

    }

    private fun movePlayerView(playerPosX: Int, playerPosY: Int){
        playerView.apply {
            translationX = playerPosX.toFloat()
            translationY = playerPosY.toFloat()
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