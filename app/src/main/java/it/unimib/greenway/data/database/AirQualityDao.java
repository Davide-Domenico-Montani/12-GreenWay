package it.unimib.greenway.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import it.unimib.greenway.model.AirQuality;

@Dao
public interface AirQualityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAirQuality(AirQuality airQuality);

    @Query("SELECT * FROM airquality WHERE id = :id")
    AirQuality getAirQuality(String id);
}
