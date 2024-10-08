package com.beam.tictactoe.data.datasource

import com.beam.tictactoe.data.local.dao.BoardDao
import com.beam.tictactoe.data.local.entity.MoveEntity
import com.beam.tictactoe.domain.TicTacToe
import com.beam.tictactoe.domain.move
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface BoardLocalDataSource {
    val board: Flow<TicTacToe>
    suspend fun saveMove(row: Int, column: Int)
    suspend fun reset()
}

@Singleton
class BoardRoomDataSource @Inject constructor(
    private val boardDao: BoardDao,
) : BoardLocalDataSource {

    override val board: Flow<TicTacToe>
        get() = boardDao.getBoard().map { it.toTicTacToe() }

    override suspend fun saveMove(row: Int, column: Int) {
        boardDao.saveMove(MoveEntity(0, row, column))
    }

    override suspend fun reset() {
        boardDao.reset()
    }
}

fun List<MoveEntity>.toTicTacToe(): TicTacToe = fold(TicTacToe()) { acc, move ->
    acc.move(move.row, move.column)
}