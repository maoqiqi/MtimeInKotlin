package com.lovejiaming.timemovieinkotlin.databasebusiness

import android.arch.persistence.room.*

/**
 * Created by xiaoxin on 2017/8/31.
 */
//@Entity(tableName = "HaveSeen")
data class HaveSeenEntity @JvmOverloads constructor(
        @PrimaryKey(autoGenerate = true) var id: Long,
        var movieId: Int,
        var movieName: String,
        var movieCoverUrl: String = ""
)


@Dao
interface HaveSeenDao {
//    @Insert
//    fun insertHaveSeen(entity: HaveSeenEntity)
//
//    @Query("SELECT * FROM HaveSeen")
//    fun queryAllHaveSeenData(): List<HaveSeenEntity>
//
//    @Query("SELECT * FROM HaveSeen where movieId = :movieId")
//    fun queryOneHaveSeenData(movieId: Int): HaveSeenEntity
//
//    @Delete
//    fun deleteOneHaveSeen(entity: HaveSeenEntity)
}