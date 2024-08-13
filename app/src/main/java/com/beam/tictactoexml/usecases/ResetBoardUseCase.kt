package com.beam.tictactoexml.usecases

import com.beam.tictactoexml.data.BoardRepository
import javax.inject.Inject

class ResetBoardUseCase @Inject constructor(
    private val boardRepository: BoardRepository
) {

    suspend operator fun invoke() = boardRepository.reset()
}