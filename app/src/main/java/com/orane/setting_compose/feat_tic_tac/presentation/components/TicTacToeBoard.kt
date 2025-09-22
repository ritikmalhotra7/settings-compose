package com.orane.setting_compose.feat_tic_tac.presentation.components

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orane.setting_compose.feat_core.presentation.ui.theme.BitCountFontFamily
import com.orane.setting_compose.feat_tic_tac.utils.CellState
import com.orane.setting_compose.feat_tic_tac.utils.Constants
import com.orane.setting_compose.feat_tic_tac.utils.Constants.GRID_SIZE
import com.orane.setting_compose.feat_tic_tac.utils.Player

@Composable
fun TicTacToeBoard(
    modifier: Modifier = Modifier,
    items: List<CellState> = List(Constants.CELL_COUNT){CellState.Empty},
    currentPlayer: Player = Player.X,
    onItemChanged: ((position: Int, currentPlayer:Player) -> Unit)? = null,
    indicatorIndex: Int? = null
) {
    val lineColor: Color = MaterialTheme.colorScheme.primary
    val lineWidth: Dp = 8.dp
    val infiniteTransition = rememberInfiniteTransition()
    val alpha = infiniteTransition
        .animateValue(
            initialValue = 0f,
            targetValue = 0.8f,
            animationSpec = infiniteRepeatable(animation = tween(
                durationMillis = 700,
                delayMillis = 100, easing = LinearOutSlowInEasing
            ), repeatMode = RepeatMode.Reverse),
            typeConverter = Float.VectorConverter
        )
    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Keep it square
                .padding(16.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val lineStrokeWidth = lineWidth.toPx()

            // Vertical lines
            for (i in 1..2) {
                drawLine(
                    color = lineColor,
                    start = Offset(x = (canvasWidth / Constants.GRID_SIZE) * i, y = 0f),
                    end = Offset(x = (canvasWidth / GRID_SIZE) * i, y = canvasHeight),
                    strokeWidth = lineStrokeWidth
                )
            }

            // Horizontal lines
            for (i in 1..2) {
                drawLine(
                    color = lineColor,
                    start = Offset(x = 0f, y = (canvasHeight / 3) * i),
                    end = Offset(x = canvasWidth, y = (canvasHeight / 3) * i),
                    strokeWidth = lineStrokeWidth
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(Constants.GRID_SIZE),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Keep it square
                .padding(16.dp)
        ) {
            items(items.size) { index ->
                val item = items[index]
                Button(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxSize().alpha(alpha = if(indicatorIndex == index) alpha.value else 1f),
                    enabled = item is CellState.Empty,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Transparent,
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp),
                    onClick = {
                        onItemChanged?.invoke(index, currentPlayer)
                    }) {
                    Text(text = item.display(), style = TextStyle(
                        color = if (item is CellState.Occupied && item.player == Player.X) Color.Cyan else Color.Red,
                        fontSize = 72.sp,
                        fontFamily = BitCountFontFamily
                    )
                    )
                }
            }
        }
    }
}