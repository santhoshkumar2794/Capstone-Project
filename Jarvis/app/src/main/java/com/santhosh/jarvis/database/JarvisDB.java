package com.santhosh.jarvis.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.util.Log;

import com.santhosh.jarvis.database.room.AppletDao;
import com.santhosh.jarvis.database.room.AppletEntity;

/**
 * Created by Santhosh on 23/07/17.
 */

@Database(entities = {AppletEntity.class}, version = 1)
public abstract class JarvisDB extends RoomDatabase {

    private static JarvisDB JARVIS_DB;
    public abstract AppletDao appletDao();


    public static JarvisDB getInMemoryDatabase(Context context) {
        if (JARVIS_DB == null) {
            JARVIS_DB =
                    Room.databaseBuilder(context, JarvisDB.class,"jarvis_db")
                    .allowMainThreadQueries()
                    .build();
                    /*Room.inMemoryDatabaseBuilder(context.getApplicationContext(), JarvisDB.class)
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            // To simplify the codelab, allow queries on the main thread.
                            .allowMainThreadQueries()
                            .build();*/
        }
        return JARVIS_DB;
    }

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.e("migrate","here");
            //database.execSQL("ALTER TABLE AppletEntity " + " ADD COLUMN price INTEGER");
        }
    };
}
