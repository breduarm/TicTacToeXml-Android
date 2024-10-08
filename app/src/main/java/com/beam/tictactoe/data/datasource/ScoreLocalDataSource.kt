package com.beam.tictactoe.data.datasource

import com.beam.tictactoe.data.local.dao.ScoreDao
import com.beam.tictactoe.data.local.entity.mapper.toEntity
import com.beam.tictactoe.data.local.entity.mapper.toScore
import com.beam.tictactoe.domain.Score
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface ScoreLocalDataSource {
    val scores: Flow<List<Score>>
    suspend fun addScore(score: Score)
}

@Singleton
class ScoreRoomDataSource @Inject constructor(
    private val scoreDao: ScoreDao,
) : ScoreLocalDataSource {

    override val scores: Flow<List<Score>>
        get() = scoreDao.getScores().map { scores -> scores.map { it.toScore() } }

    override suspend fun addScore(score: Score) {
        scoreDao.save(score.toEntity())
    }
}