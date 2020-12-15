package com.example.mappactice8_12_2020.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mappactice8_12_2020.data.model.Routes;

@Database(entities = {Routes.class}, version = 1, exportSchema = false)
public abstract class RoutesDatabase extends RoomDatabase {

    public static RoutesDatabase instance;

    public abstract RoutesDao routesDao();

    private static final String DB_NAME = "routes.db";

    public static RoutesDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, RoutesDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
