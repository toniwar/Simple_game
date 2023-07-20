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
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.use_cases.MovePlayerUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameWindow : Fragment() {
    private val player by lazy { PlayerView(requireContext())}
    private lateinit var viewModel: GameWindowViewModel
    private lateinit var params:LayoutParams
    private var pos: Point? = null
    
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
            viewModel.state.collect{ list->
                list.forEach{
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onResume() {
        super.onResume()
        mainScope.launch {
            viewModel.playerLocation.collect {
                pos = it

                if (pos == null) {
                    delay(50)
                    pos = getStartLocation()

                }
                viewModel.setPlayerPosition(pos!!)
                createPlayer(params, pos!!.x, pos!!.y - 60)

                Log.d("CellPos", "${pos!!.x}, ${pos!!.y}")
            }

        }
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
                val accessStep =movePlayerUseCase.move(player.user,
                    pos!!.x,
                    pos!!.y,
                    clickLocation.x,
                    clickLocation.y,
                    player.user.position,
                    it.id
                )
                if(accessStep){
                    pos = clickLocation
                    player.user.position = it.id
                    movePlayer(pos!!.x, pos!!.y - 60)
                    viewModel.setPlayerPosition(pos!!)
                }

            }

        }


    }

    private fun createPlayer(params: LayoutParams, playerPosX: Int, playerPosY: Int){
        binding.root.removeView(player)
        player.apply {
            if(layoutParams == null) layoutParams = params
            binding.root.addView(player)
            translationX = playerPosX.toFloat()
            translationY = playerPosY.toFloat()

        }

    }

    private fun movePlayer(playerPosX: Int, playerPosY: Int){
        player.apply {
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