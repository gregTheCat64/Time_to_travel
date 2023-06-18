package com.example.timetotravel.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverter
import com.example.timetotravel.models.Seat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {
    @Query("SELECT * FROM FlightEntity")
    fun getAll(): Flow<List<FlightEntity>>

    @Query("SELECT * FROM FlightEntity WHERE searchToken = :token")
    suspend fun getByToken(token: String): FlightEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(flights: List<FlightEntity>)

    @Query("""
        UPDATE FlightEntity SET
        isFav = CASE WHEN iSFav THEN 0 ELSE 1 END
        WHERE searchToken = :token
    """)
    suspend fun setFav(token: String)

}


private val typeToken = object : TypeToken<List<Seat>>() {}.type
class Converters {
    @TypeConverter
    fun convertListToJSON(list: List<Seat>): String = Gson().toJson(list)
    @TypeConverter
    fun convertJSONToList(json: String) = Gson().fromJson<List<Seat>>(json, typeToken)
}