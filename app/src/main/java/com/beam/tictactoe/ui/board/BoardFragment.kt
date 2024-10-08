package com.beam.tictactoe.ui.board

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.beam.tictactoe.R
import com.beam.tictactoe.databinding.FragmentBoardBinding
import com.beam.tictactoe.domain.Draw
import com.beam.tictactoe.domain.GameState
import com.beam.tictactoe.domain.O
import com.beam.tictactoe.domain.Winner
import com.beam.tictactoe.domain.X
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BoardFragment : Fragment(R.layout.fragment_board) {

    private val viewModel: BoardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentBoardBinding.bind(view).init()
    }

    private fun FragmentBoardBinding.init() {
        boardView.onClick = viewModel::move

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when(state.gameState) {
                        GameState.NotStarted -> bindNotStarted()
                        GameState.InProgress -> bindInProgress()
                        is GameState.Finished -> bindFinished(state.gameState.winner)
                    }
                    boardView.update(state)
                }
            }
        }
    }

    private fun FragmentBoardBinding.bindNotStarted() {
        boardView.visibility = View.GONE

        message.text = getString(R.string.welcome)
        message.visibility = View.VISIBLE

        startBtn.text = getString(R.string.start_game)
        startBtn.visibility = View.VISIBLE
        startBtn.setOnClickListener { viewModel.startGame() }
    }

    private fun FragmentBoardBinding.bindInProgress() {
        boardView.visibility = View.VISIBLE
        message.visibility = View.GONE
        startBtn.visibility = View.GONE
    }

    private fun FragmentBoardBinding.bindFinished(winner: Winner) {
        boardView.visibility = View.GONE

        message.visibility = View.VISIBLE
        message.text = when(winner) {
            Draw -> getString(R.string.draw)
            X, O -> getString(R.string.winner, winner.toString())
        }

        startBtn.visibility = View.VISIBLE
        startBtn.text = getString(R.string.play_again)
        startBtn.setOnClickListener { viewModel.resetGame() }
    }
}