package com.example.mappactice8_12_2020.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mappactice8_12_2020.data.model.Routes;

import java.util.List;

@Dao
public interface RoutesDao {
    @Insert
    void insert(Routes run);

    @Delete
    void delete(Routes routes);

    @Query("SELECT * FROM routes ORDER BY id DESC")
    LiveData<List<Routes>> getAllRun();


}
