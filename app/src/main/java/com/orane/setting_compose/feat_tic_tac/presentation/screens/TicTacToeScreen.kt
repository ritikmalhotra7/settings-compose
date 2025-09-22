package com.orane.setting_compose.feat_tic_tac.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.orane.setting_compose.R
import com.orane.setting_compose.feat_core.presentation.ui.theme.SettingComposeTheme
import com.orane.setting_compose.feat_tic_tac.presentation.components.TicTacToeBoard
import com.orane.setting_compose.feat_tic_tac.presentation.viewModels.TicTacToeViewModel
import com.orane.setting_compose.feat_tic_tac.utils.Player

@Composable
fun TicTacToeScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current.applicationContext
    val viewModel = hiltViewModel<TicTacToeViewModel>()

    val crossScore = viewModel.crossScore.collectAsStateWithLifecycle()
    val circleScore = viewModel.circleScore.collectAsStateWithLifecycle()

    val currentPlayer = viewModel.currentPlayer.collectAsStateWithLifecycle()

    val cellsState = viewModel.cellsState.collectAsStateWithLifecycle()
    val winner = viewModel.winner.collectAsStateWithLifecycle()
    val indicatorIndex = viewModel.indicatorIndex.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "TIC TAC TOE",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "Cross Score", fontSize = 16.sp)
                Text(
                    text = crossScore.value.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                )
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "Circle Score", fontSize = 16.sp)
                Text(
                    text = circleScore.value.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if (winner.value != null) {
            Text(
                text = "Winner is ${winner.value}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                onClick = {
                    viewModel.onRestart()
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = Color(0xFF0B9700)
                )
            ) {
                Text(text = "Restart", style = MaterialTheme.typography.titleMedium, color = Color.White)
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.baseline_restart_alt_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        } else {
            TicTacToeBoard(
                items = cellsState.value,
                onItemChanged = { index, currentPlayer ->
                    viewModel.onItemChanged(index)
                },
                indicatorIndex = indicatorIndex.value
            )
            Text(
                text = when (currentPlayer.value) {
                    Player.X -> "Current Player: X"
                    Player.O -> "Current Player: O"
                },
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun preview() {
    SettingComposeTheme {
        TicTacToeScreen(
            navController = rememberNavController()
        )
    }
}