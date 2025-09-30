package com.orane.setting_compose.feat_tic_tac.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.orane.setting_compose.feat_tic_tac.utils.CellState
import com.orane.setting_compose.feat_tic_tac.utils.Constants
import com.orane.setting_compose.feat_tic_tac.utils.Constants.CELL_COUNT
import com.orane.setting_compose.feat_tic_tac.utils.Constants.GRID_SIZE
import com.orane.setting_compose.feat_tic_tac.utils.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TicTacToeViewModel @Inject constructor():ViewModel() {

    private val mCurrentPlayer = MutableStateFlow(Player.X)
    val currentPlayer = mCurrentPlayer.asStateFlow()

    private val mCrossScore = MutableStateFlow(0)
    val crossScore = mCrossScore.asStateFlow()

    private val mCircleScore = MutableStateFlow(0)
    val circleScore = mCircleScore.asStateFlow()

    private val mCellsState = MutableStateFlow<List<CellState>>(List<CellState>(CELL_COUNT){CellState.Empty})
    val cellsState = mCellsState.asStateFlow()

    private val mIndicatorIndex = MutableStateFlow<Int?>(null)
    val indicatorIndex = mIndicatorIndex.asStateFlow()

    private val mWinner = MutableStateFlow<Player?>(null)
    val winner = mWinner.asStateFlow()

    private val queue = ArrayDeque<Int>()

    private fun checkForWin(index:Int):Boolean{
        val n = Constants.GRID_SIZE
        val row = index / n
        val col = index % n
        val player = cellsState.value[index]
        val k = n

        // Directions: (dr, dc)
        val directions = arrayOf(
            intArrayOf(0, 1),   // horizontal →
            intArrayOf(1, 0),   // vertical ↓
            intArrayOf(1, 1),   // diagonal ↘
            intArrayOf(1, -1)   // anti-diagonal ↙
        )

        fun count(dr: Int, dc: Int): Int {
            var r = row + dr
            var c = col + dc
            var count = 0
            while (r in 0 until n && c in 0 until n) {
                val idx = r * n + c
                Log.d("taget-idx",idx.toString())
                if (cellsState.value[idx] == player) {
                    count++
                    r += dr
                    c += dc
                } else break
            }
            return count
        }

        for ((dr, dc) in directions) {
            val total = 1 + count(dr, dc) + count(-dr, -dc)
            if (total >= k) return true
        }
        return false
    }
    fun onItemChanged(position:Int){
        queue.add(position)
        var posToRemove :Int? = null
        mCellsState.update {
            if(queue.isNotEmpty() && queue.size>Constants.GRID_SIZE*2) posToRemove = queue.removeFirst()
            mIndicatorIndex.update{
                if(queue.size>((Constants.GRID_SIZE*2)-1)) queue.first()
                else null
            }
            it.mapIndexed { index, cellState ->
                if(posToRemove != null && index == posToRemove){
                    CellState.Empty
                }else{
                    if(index == position && cellState is CellState.Empty){
                        CellState.Occupied(currentPlayer.value)
                    }else{
                        cellState
                    }
                }
            }
        }
        if(checkForWin(position)){
            mWinner.update{
                currentPlayer.value
            }
            if(currentPlayer.value == Player.X) {
                mCrossScore.update { it + 1 }
            }else{
                mCircleScore.update { it + 1 }
            }
        }
        mCurrentPlayer.update{
            when(it){
                Player.X -> Player.O
                Player.O -> Player.X
            }
        }
    }

    fun onRestart(){
        mWinner.update{ null }
        mCurrentPlayer.update{ Player.X }
        mCellsState.update {
            List<CellState>(9) { CellState.Empty }
        }
        queue.clear()
    }


}