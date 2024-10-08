package com.beam.tictactoe.data.repository

import com.beam.tictactoe.data.datasource.BoardLocalDataSourceFake
import com.beam.tictactoe.domain.TicTacToe
import com.beam.tictactoe.domain.move
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class BoardRepositoryIntTest {

    @Test
    fun `when board is called, then return board from local data source`() = runTest {
        val expectedBoard: TicTacToe = TicTacToe()
            .move(0, 0)
            .move(1, 1)
        val localDataSource = BoardLocalDataSourceFake(expectedBoard)
        val boardRepository = BoardRepository(localDataSource)

        val actualBoard: TicTacToe = boardRepository.board.first()

        assertEquals(expectedBoard, actualBoard)
    }

    @Test
    fun `When move is called, then save move in local data source`() = runTest {
        val expectedBoard: TicTacToe = TicTacToe().move(0, 0)
        val localDataSource = BoardLocalDataSourceFake()
        val boardRepository = BoardRepository(localDataSource)

        boardRepository.move(0, 0)
        val actualBoard: TicTacToe = boardRepository.board.first()

        assertEquals(expectedBoard, actualBoard)
    }

    @Test
    fun `When reset is called, then reset board in local data source`() = runTest {
        val expectedBoard = TicTacToe()
        val initialBoard: TicTacToe = TicTacToe()
            .move(0, 0)
            .move(1, 1)
        val localDataSource = BoardLocalDataSourceFake(initialBoard)
        val boardRepository = BoardRepository(localDataSource)

        boardRepository.reset()
        val actualBoard: TicTacToe = boardRepository.board.first()

        assertEquals(expectedBoard, actualBoard)
    }
}