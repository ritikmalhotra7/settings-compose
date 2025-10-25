package com.orane.setting_compose.feat_tic_tac_toe.presentation.viewmodel

import com.orane.setting_compose.feat_tic_tac.presentation.viewModels.TicTacToeViewModel
import com.orane.setting_compose.feat_tic_tac.utils.CellState
import com.orane.setting_compose.feat_tic_tac.utils.Player
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.junit.Test


// Mocked Constants for testing purposes (assuming 3x3 grid)
object TestConstants {
    const val GRID_SIZE = 3
    const val CELL_COUNT = GRID_SIZE * GRID_SIZE
}
class TicTacToeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: TicTacToeViewModel

    // Setup the Test Dispatcher for all coroutines
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = TicTacToeViewModel()
    }

    // Reset the Main dispatcher after tests
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // --- Test 1: Basic State Update and Player Turn ---

    @Test
    @DisplayName("GIVEN empty board WHEN item changed THEN cell is occupied and player switches")
    fun onItemChanged_updatesCellAndSwitchesPlayer() = runTest {
        // 1. Initial State Check
        assertEquals(Player.X, viewModel.currentPlayer.value)

        // 2. Action: Player X plays at position 4
        viewModel.onItemChanged(4)
        advanceUntilIdle() // Wait for flows to process

        // 3. Assertion 1: Cell state updated
        assertEquals(CellState.Occupied(Player.X), viewModel.cellsState.value[4])

        // 4. Assertion 2: Player switched to O
        assertEquals(Player.O, viewModel.currentPlayer.value)
    }

    // --- Test 2: Win Condition and Score Update ---

    @Test
    @DisplayName("GIVEN game almost won WHEN winning move played THEN winner and score update")
    fun onItemChanged_checksForWinAndUpdatesScore() = runTest {
        // Setup: Simulate X playing 0, 1
        viewModel.onItemChanged(0) // X plays 0 (Player O's turn)
        viewModel.onItemChanged(3) // O plays 3 (Player X's turn)
        viewModel.onItemChanged(1) // X plays 1 (Player O's turn)
        viewModel.onItemChanged(4) // O plays 4 (Player X's turn)
        advanceUntilIdle()

        // Initial score check
        assertEquals(0, viewModel.crossScore.value)
        assertNull(viewModel.winner.value)

        // Action: X plays the winning move (position 2: 0, 1, 2 is a win)
        viewModel.onItemChanged(2)
        advanceUntilIdle()

        // Assertion 1: Winner is X
        assertEquals(Player.X, viewModel.winner.value)

        // Assertion 2: Score is updated
        assertEquals(1, viewModel.crossScore.value)

        // Assertion 3: Player still switches even after win (ready for next round)
        assertEquals(Player.O, viewModel.currentPlayer.value)
    }

    // --- Test 3: Sliding Window/Queue Logic ---

    @Test
    @DisplayName("GIVEN 6 moves WHEN 7th move played THEN oldest cell is cleared")
    fun onItemChanged_managesSlidingWindow() = runTest {
        // The queue limit is GRID_SIZE * 2, which is 6 for a 3x3 grid.
        // Removal starts when size > 6, i.e., when the 7th item is added.

        // Action: Play 6 moves (0 to 5)
        for (i in 0..5) {
            viewModel.onItemChanged(i) // X, O, X, O, X, O...
        }
        advanceUntilIdle()

        // At this point, the queue size is 6. All cells 0-5 should be occupied.
        assertEquals(CellState.Occupied(Player.O), viewModel.cellsState.value[5])

        // Indicator Check (size is 6, so queue.first() is 0)
        assertEquals(0, viewModel.indicatorIndex.value)

        // Action: Play the 7th move (at position 6)
        // This move adds 6 to the queue, making the size 7.
        // 1. posToRemove will be 0 (queue.removeFirst())
        // 2. Cell 0 should be cleared.
        viewModel.onItemChanged(6)
        advanceUntilIdle()

        // Assertion 1: Oldest cell (position 0) is cleared
        assertEquals(CellState.Empty, viewModel.cellsState.value[0])

        // Assertion 2: Newest cell (position 6) is occupied by X
        assertEquals(CellState.Occupied(Player.X), viewModel.cellsState.value[6])

        // Assertion 3: Indicator Index updated to the new oldest item (position 1)
        assertEquals(1, viewModel.indicatorIndex.value)
    }
}