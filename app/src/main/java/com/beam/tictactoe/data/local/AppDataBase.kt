package com.beam.tictactoe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.beam.tictactoe.data.local.dao.BoardDao
import com.beam.tictactoe.data.local.dao.ScoreDao
import com.beam.tictactoe.data.local.entity.MoveEntity
import com.beam.tictactoe.data.local.entity.ScoreEntity

@Database(entities = [MoveEntity::class, ScoreEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun boardDao(): BoardDao
    abstract fun scoreDao(): ScoreDao
}