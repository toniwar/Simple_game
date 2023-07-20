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
import com.example.simplegame.domain.models.Enemy
import com.example.simplegame.domain.models.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            loadToState()
            setGameUnitState(playerUseCase.createNewPlayer())
            setGameUnitState(enemyUseCase.createNewEnemy())
        }
    }

    private fun loadToState(){
        _state.value = downloadGameFieldUC.downloadField()
    }



    fun setGameUnitState(gameUnit: GameUnit){
        when(gameUnit){
            is Player -> _playerState.value = gameUnit
            is Enemy -> _enemyState.value = gameUnit
        }

    }


}