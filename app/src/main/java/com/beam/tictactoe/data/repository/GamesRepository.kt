package com.beam.tictactoe.data.repository

import com.beam.tictactoe.data.datasource.GamesRemoteDataSource
import com.beam.tictactoe.domain.VideoGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GamesRepository @Inject constructor(
    private val remoteDataSource: GamesRemoteDataSource,
) {

    val remoteGames: Flow<List<VideoGame>>
        get() = flow { emit(remoteDataSource.getGames()) }
}
