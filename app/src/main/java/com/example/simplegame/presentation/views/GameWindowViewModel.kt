package com.example.simplegame.presentation.views

import androidx.lifecycle.ViewModel
import com.example.simplegame.data.GameRepositoryImpl
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.models.GameUnit
import com.example.simplegame.domain.use_cases.DownloadGameFieldUC
import com.example.simplegame.domain.use_cases.PlayerUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class GameWindowViewModel: ViewModel() {
    private val gameField = mutableListOf<GameCell>()

    private val _gameUnitState = MutableStateFlow<GameUnit?>(null)
    val gameUnitState get() = _gameUnitState

    private val _state = MutableStateFlow<List<GameCell>>(gameField)
    val state get() = _state


    private val repository = GameRepositoryImpl()
    private val downloadGameFieldUC = DownloadGameFieldUC(repository)
    private val playerUseCase = PlayerUseCase(repository)
    init {
        setGameUnitState(playerUseCase.createNewPlayer())
        loadToState()
    }

    private fun loadToState(){
        _state.value = downloadGameFieldUC.downloadField()
    }



    fun setGameUnitState(gameUnit: GameUnit){
        _gameUnitState.value = gameUnit

    }


}