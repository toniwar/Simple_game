package com.example.simplegame.presentation.fragments

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
import com.example.simplegame.R
import com.example.simplegame.databinding.FragmentGameWindowBinding
import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.ExitCell
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.models.GameUnit
import com.example.simplegame.domain.models.Player
import com.example.simplegame.domain.use_cases.MoveEnemyUseCase
import com.example.simplegame.domain.use_cases.MovePlayerUseCase
import com.example.simplegame.presentation.views.GameCellView
import com.example.simplegame.presentation.view_models.GameWindowViewModel
import com.example.simplegame.presentation.view_models.MainViewModel
import com.example.simplegame.presentation.views.EnemyView
import com.example.simplegame.presentation.views.ExitView
import com.example.simplegame.presentation.views.PlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class GameWindow : Fragment() {
    private val playerView by lazy { PlayerView(requireContext()) }
    private val enemyView by lazy { EnemyView(requireContext())}
    private lateinit var player: Player
    private lateinit var enemy: Enemy
    private lateinit var viewModel: GameWindowViewModel
    private lateinit var mainVM: MainViewModel
    private lateinit var params:LayoutParams
    private var exitId = 0

    
    private val binding by lazy {
        FragmentGameWindowBinding.inflate(layoutInflater)
    }
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val cells = mutableListOf<GameCell>()
    private lateinit var activity: Activity
    private val movePlayerUseCase by lazy { MovePlayerUseCase() }
    private val moveEnemyUseCase by lazy { MoveEnemyUseCase() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = requireActivity()
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val screenWidth = metrics.widthPixels
        val cellWidth = screenWidth/5
        params = LayoutParams(cellWidth, cellWidth)
        viewModel = ViewModelProvider(requireActivity())[GameWindowViewModel::class.java]
        mainVM = ViewModelProvider(requireActivity())[MainViewModel::class.java]


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
            viewModel.playerState.collect{
                it?.let {
                    player = it
                    setGameUnitState(player, playerView)
                }
            }
        }
        mainScope.launch {
            viewModel.enemyState.collect{
                it?.let {
                    enemy = it
                    setGameUnitState(enemy, enemyView)
                }
            }
        }

    }

    private suspend fun setGameUnitState(unit: GameUnit, view: View) {
        if(unit.location == null){
            delay(50)
            unit.location = getStartLocation(unit.position)
        }


        val pos = unit.location

        createGameUnitView(params, pos!!.x, pos.y -60, view)


    }

    private fun getStartLocation(i: Int): Point{
        return binding.gameGrid[i-1].getLocation()
    }

    override fun onStop() {
        super.onStop()
        mainScope.cancel()
    }


    private fun createGameField(params: LayoutParams){
        for(i in cells.indices){
            val cell = if(cells[i] !is ExitCell)GameCellView(activity)
            else ExitView(activity)

            cell.apply{
                layoutParams = params
                id = i+1
                if(cells[i] is ExitCell){
                    exitId = id

                }

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
                    moveView(player.location!!.x, player.location!!.y - 60, playerView)
                    enemyMovement()
                    viewModel.setGameUnitState(player)
                    if(player.position == exitId){
                        mainVM.sendData("Victory")

                    }


                }

            }

        }


    }


    private fun createGameUnitView(params: LayoutParams, posX: Int, posY: Int, view: View){
        binding.root.removeView(view)
        view.apply {
            if(layoutParams == null) layoutParams = params
            binding.root.addView(view)
            translationX = posX.toFloat()
            translationY = posY.toFloat()

        }

    }

    private fun moveView(posX: Int, posY: Int, view: View){
        view.apply {
            translationX = posX.toFloat()
            translationY = posY.toFloat()
        }
    }

    private fun enemyMovement(){
        mainScope.launch {
            enemy.apply {
                player.isPlayerTurn = false
                isEnemyTurn = true
                position = moveEnemyUseCase.move(this, player)
                location = getStartLocation(position)
                delay(200)
                moveView(location!!.x, location!!.y - 60, enemyView)
                isEnemyTurn = false
                player.isPlayerTurn = true
                viewModel.setGameUnitState(this)
                if(player.position == position){
                    mainVM.sendData("Lose")

                }

            }
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