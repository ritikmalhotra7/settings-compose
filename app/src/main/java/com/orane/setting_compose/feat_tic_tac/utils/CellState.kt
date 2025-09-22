package com.orane.setting_compose.feat_tic_tac.utils

sealed interface CellState {
    data object Empty:CellState
    data class Occupied(val player:Player):CellState

    fun display():String = when(this){
        is Empty -> ""
        is Occupied -> player.name
    }
}