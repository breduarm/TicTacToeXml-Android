package com.beam.tictactoexml.ui.scoreboard

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.beam.tictactoexml.R
import com.beam.tictactoexml.databinding.FragmentScoreboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScoreboardFragment : Fragment(R.layout.fragment_scoreboard) {

    private val viewModel: ScoreboardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FragmentScoreboardBinding.bind(view).init()

        viewModel.onUiReady()
    }

    private fun FragmentScoreboardBinding.init() {
        val adapter = ScoreboardAdapter()
        score.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { uiState ->
                    adapter.submitList(uiState.scores)
                    emptyMessage.isVisible = uiState.scores.isEmpty()
                }
            }
        }
    }
}