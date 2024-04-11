package it.unimib.greenway.data.database;

import static it.unimib.greenway.util.Constants.AIRQUALITY_DATABASE_NAME;
import static it.unimib.greenway.util.Constants.DATABASE_VERSION;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.unimib.greenway.model.AirQuality;

@Database(entities = {AirQuality.class}, version = DATABASE_VERSION)
abstract public class AirQualityDatabase extends RoomDatabase {


    public abstract AirQualityDao airQualityDao();

    private static volatile AirQualityDatabase INSTANCE;
    private static final int NUMBER_OF_THREAD = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREAD);

    public static AirQualityDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AirQualityDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AirQualityDatabase.class, AIRQUALITY_DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }


}
