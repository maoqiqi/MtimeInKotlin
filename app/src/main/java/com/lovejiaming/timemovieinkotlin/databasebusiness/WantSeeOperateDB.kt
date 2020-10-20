package com.lovejiaming.timemovieinkotlin.databasebusiness

import android.arch.persistence.room.*

/**
 * Created by xiaoxin on 2017/8/31.
 */

//@Entity(tableName = "WantSee")
data class WantSeeEntity @JvmOverloads constructor(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        var movieId: Int,
        var movieName: String,
        var movieCoverUrl: String
)

@Dao
interface WantSeeDao {
//    @Insert
//    fun insertWantSee(entity: WantSeeEntity)
//
//    @Query("SELECT * FROM WantSee")
//    fun queryAllWantSeeData(): List<WantSeeEntity>
//
//    @Query("SELECT * FROM WantSee where movieId = :movieId")
//    fun queryOneWantSeeData(movieId: Int): WantSeeEntity
//
//    @Delete()
//    fun deleteOneWantSee(entity: WantSeeEntity)
}
