package com.beam.tictactoe.composeui.screens.scoreboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beam.tictactoe.R
import com.beam.tictactoe.domain.O
import com.beam.tictactoe.domain.Score
import com.beam.tictactoe.domain.X
import com.beam.tictactoe.ui.formatToString
import com.beam.tictactoe.ui.scoreboard.ScoreboardViewModel
import java.util.Date

@Composable
fun ScoreboardScreen(viewModel: ScoreboardViewModel = hiltViewModel()) {
    val state: ScoreboardViewModel.UiState by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.onUiReady()
    }

    ScoreboardContent(state)
}

@Composable
fun ScoreboardContent(state: ScoreboardViewModel.UiState) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.scores.isEmpty()) {
            Text(
                text = stringResource(id = R.string.empty_scoreboard),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            ScoreList(scores = state.scores)
        }
    }
}

@Composable
fun ScoreList(scores: List<Score>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(scores) {
            ScoreItem(it)
        }
    }
}

@Composable
fun ScoreItem(score: Score) {
    Card {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(style = MaterialTheme.typography.h5, text = score.winner.toString())
            Text(modifier = Modifier.weight(1f), text = "Number of moves: ${score.numberOfMoves}")
            Text(text = score.date.formatToString())
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ScoreboardScreenPreview() {
    val scoresMock: List<Score> = listOf(
        Score(winner = X, numberOfMoves = 10, date = Date()),
        Score(winner = O, numberOfMoves = 8, date = Date()),
    )
    val stateMock = ScoreboardViewModel.UiState(
        scores = scoresMock,
    )

    ScoreboardContent(stateMock)
}