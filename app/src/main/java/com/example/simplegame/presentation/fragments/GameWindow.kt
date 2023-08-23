package com.example.simplegame.presentation.fragments

import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.LayoutParams
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.simplegame.databinding.FragmentGameWindowBinding
import com.example.simplegame.domain.models.EmptyCell
import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.ExitCell
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.models.GameUnit
import com.example.simplegame.domain.models.NotEmptyCell
import com.example.simplegame.domain.models.Player
import com.example.simplegame.domain.models.Wall
import com.example.simplegame.domain.use_cases.MoveEnemyUseCase
import com.example.simplegame.domain.use_cases.MovePlayerUseCase
import com.example.simplegame.presentation.views.GameCellView
import com.example.simplegame.presentation.view_models.GameWindowViewModel
import com.example.simplegame.presentation.view_models.MainViewModel
import com.example.simplegame.presentation.views.EnemyView
import com.example.simplegame.presentation.views.ExitView
import com.example.simplegame.presentation.views.PlayerView
import com.example.simplegame.presentation.views.WallView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    private var actionBarHeight = 0.0f

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GameWindowViewModel::class.java]
        mainVM = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel.refresh()

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
        val onBackClick = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewModel.refresh()
                mainVM.sendData("Home")
            }
        }
        (requireActivity()).onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackClick)

        activity = requireActivity()



        val screenWidth = resources.displayMetrics.widthPixels
        val cellWidth = screenWidth/5
        params = LayoutParams(cellWidth, cellWidth)

        val typedValue = TypedValue()
        if(activity.theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)){
            actionBarHeight = TypedValue.complexToDimension(typedValue.data, resources.displayMetrics)



        }

        job = mainScope.launch{
            viewModel.state.collect { list ->
                list.forEach {
                    cells.add(it)
                }
                Log.d("Cells", cells.size.toString())
                createGameField(params)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                job = mainScope.launch {
                    viewModel.playerState.collect{
                        it?.let {
                            player = it
                            setGameUnitState(player, playerView)
                        }
                    }
                }

                job = mainScope.launch {
                    viewModel.enemyState.collect{
                        it?.let {
                            enemy = it
                            setGameUnitState(enemy, enemyView)
                        }
                    }
                }

            }

        }

    }

    private suspend fun setGameUnitState(unit: GameUnit, view: View) {
        if(unit.location == null){
            delay(10)
            unit.location = getStartLocation(unit.position)
        }

        val pos = unit.location

        createGameUnitView(pos!!.x, pos.y, view)

    }

    private fun getStartLocation(i: Int): Point{
        return binding.gameGrid[i-1].getLocation()
    }
    private fun analiseView(i: Int) = binding.gameGrid[i-1]

    override fun onStop() {
        super.onStop()
        job.cancel()
    }


    private fun createGameField(params: LayoutParams){
        for(i in cells.indices ){
            val cell =
                when(cells[i]){
                    is EmptyCell -> GameCellView(activity)
                    is ExitCell -> ExitView(activity)
                    is Wall -> WallView(activity)
                    is NotEmptyCell -> GameCellView(activity)
                }

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
                    it
                )
                if(accessStep){
                    player.location = clickLocation
                    player.position = it.id
                    moveView(player.location!!.x, player.location!!.y  , playerView)
                    enemyMovement()
                    viewModel.setGameUnitState(player)
                    if(player.position == exitId){
                        mainVM.sendData("Victory")

                    }


                }

            }

        }

    }


    private fun createGameUnitView( posX: Int, posY: Int, view: View){
        binding.root.removeView(view)
        view.apply {
            val cView = binding.gameGrid[0]

            if(layoutParams == null){

                val w = cView.width
                val h = cView.height
                layoutParams = LayoutParams(w,h)
            }
            binding.root.addView(view)
            translationX = posX.toFloat()
            translationY = posY.toFloat() - actionBarHeight/2 - (3*resources.displayMetrics.density)

        }
    }

    private fun moveView(posX: Int, posY: Int, view: View){

        ObjectAnimator.ofFloat(view, "translationX", posX.toFloat()).apply {
            duration = 500
            start()
        }
        ObjectAnimator.ofFloat(view, "translationY", posY.toFloat() - actionBarHeight/2 - (3*resources.displayMetrics.density)).apply {
            duration = 500
            start()
        }
    }




    private fun enemyMovement(){
        job = mainScope.launch {
            enemy.apply {
                player.isPlayerTurn = false
                isEnemyTurn = true
                var stepCounter = 0
                val previousPos = position
                while(stepCounter < 2) {
                    position = moveEnemyUseCase.move(this, player, binding.gameGrid, previousPos)
                    location = getStartLocation(position)
                    delay(500)
                    moveView(location!!.x, location!!.y  , enemyView)
                    if(player.position == position) {
                        delay(500)
                        mainVM.sendData("Lose")
                        break
                    }
                    stepCounter++

                }
                isEnemyTurn = false
                player.isPlayerTurn = true
                viewModel.setGameUnitState(this)


            }
        }

    }

    companion object{

        fun View.getLocation(): Point{
            val location = IntArray(2)
            getLocationOnScreen(location)
            return Point(location[0], location[1])
        }


    }

}