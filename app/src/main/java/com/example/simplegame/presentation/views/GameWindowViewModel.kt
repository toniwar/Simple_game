package com.example.simplegame.presentation.views

import androidx.lifecycle.ViewModel
import com.example.simplegame.data.GameRepositoryImpl
import com.example.simplegame.domain.models.GameCell
import com.example.simplegame.domain.use_cases.DownloadGameFieldUC
import kotlinx.coroutines.flow.MutableStateFlow

class GameWindowViewModel: ViewModel() {
    private val gameField = mutableListOf<GameCell>()
    private val _state = MutableStateFlow<List<GameCell>>(gameField)
    val state get() = _state
    private val repository = GameRepositoryImpl()
    private val downloadGameFieldUC = DownloadGameFieldUC(repository)
    init {
        loadToState()
    }

    private fun loadToState(){
        _state.value = downloadGameFieldUC.downloadField()
    }
}