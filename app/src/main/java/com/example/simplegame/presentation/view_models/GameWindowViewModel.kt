package com.example.simplegame.presentation.view_models

import androidx.lifecycle.ViewModel
import com.example.simplegame.data.GameRepositoryImpl
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.models.GameUnit
import com.example.simplegame.domain.use_cases.DownloadGameFieldUC
import com.example.simplegame.domain.use_cases.EnemyUseCase
import com.example.simplegame.domain.use_cases.PlayerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import com.example.simplegame.data.levels.Level1
import com.example.simplegame.data.levels.Level2
import com.example.simplegame.data.levels.Level3
import com.example.simplegame.data.levels.Level4
import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.Level
import com.example.simplegame.domain.models.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameWindowViewModel: ViewModel() {
    private val gameField = mutableListOf<GameCell>()

    private val _playerState = MutableStateFlow<Player?>(null)
    val playerState get() = _playerState

    private val _enemyState = MutableStateFlow<Enemy?>(null)
    val enemyState get() = _enemyState

    private val _state = MutableStateFlow<List<GameCell>>(gameField)
    val state get() = _state


    private val repository = GameRepositoryImpl()
    private val downloadGameFieldUC = DownloadGameFieldUC(repository)
    private val playerUseCase = PlayerUseCase(repository)
    private val enemyUseCase = EnemyUseCase(repository)
    init {
        refresh()

    }

    private fun loadToState(){
        val level = when(Random.nextInt(4)){
            0-> Level1()
            1-> Level2()
            2-> Level3()
            else-> Level4()

        }
        _state.value = downloadGameFieldUC.downloadField(level)
    }



    fun setGameUnitState(gameUnit: GameUnit){
        when(gameUnit){
            is Player -> _playerState.value = gameUnit
            is Enemy -> _enemyState.value = gameUnit
        }

    }

    fun refresh(){
        loadToState()
        setGameUnitState(playerUseCase.createNewPlayer())
        setGameUnitState(enemyUseCase.createNewEnemy())
    }




}