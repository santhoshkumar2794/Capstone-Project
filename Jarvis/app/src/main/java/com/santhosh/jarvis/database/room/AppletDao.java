package com.santhosh.jarvis.database.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by Santhosh on 23/07/17.
 */

@Dao
public interface AppletDao {

    @Query("SELECT * FROM AppletEntity")
    List<AppletEntity> getAppletList();

    @Query("SELECT * FROM AppletEntity where situation = :situationName")
    List<AppletEntity> getAppletBySituation(String situationName);

    @Query("SELECT * FROM AppletEntity where situationType = :situationType")
    List<AppletEntity> getAppletBySituationType(String situationType);

    @Insert(onConflict = IGNORE)
    void insertAppletData(AppletEntity appletEntity);


}
