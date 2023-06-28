package com.example.timetotravel.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteTable
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverter
import androidx.room.Upsert
import com.example.timetotravel.models.Seat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {
    @Transaction
    @Query("SELECT * FROM flights")
    fun getFlightsWithSeats(): Flow<List<FlightWithSeatsEntity>>

    @Transaction
    @Query("SELECT * FROM flights WHERE searchToken = :token")
    suspend fun getByToken(token: String): FlightWithSeatsEntity?

    @Query("SELECT searchToken FROM flights")
    suspend fun getDbFlightTokens(): List<String>

    @Query("DELETE FROM flights WHERE searchToken in (:tokens)")
    suspend fun removeNotActualTokens(tokens: List<String>)

    @Transaction
    @Upsert
    suspend fun insert(flights: List<FlightEntity>, seats: List<SeatEntity>)

    @Insert
    suspend fun like(likedFlight: LikedFlightEntity)

    @Delete
    suspend fun unLike(likedFlight: LikedFlightEntity)


}


//private val typeToken = object : TypeToken<List<Seat>>() {}.type
//class Converters {
//    @TypeConverter
//    fun convertListToJSON(list: List<Seat>): String = Gson().toJson(list)
//    @TypeConverter
//    fun convertJSONToList(json: String) = Gson().fromJson<List<Seat>>(json, typeToken)
//}