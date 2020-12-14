package com.example.mappactice8_12_2020.data.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mappactice8_12_2020.data.RoutesDao;
import com.example.mappactice8_12_2020.data.RoutesDatabase;
import com.example.mappactice8_12_2020.data.model.Routes;

import java.util.List;

public class RoutesRepository {

    private RoutesDao routesDao;
    private LiveData<List<Routes>> allInventory;

    public RoutesRepository(Application application) {
        RoutesDatabase routesDatabase = RoutesDatabase.getInstance(application);
        routesDao = routesDatabase.inventoryDao();
        allInventory = routesDao.getAllRun();
    }

    public void insert(Routes routes) {
        new InsertRoutesAsyncTsk(routesDao).execute(routes);
    }

    public LiveData<List<Routes>> getAllRun() {
        return allInventory;
    }

    private static class InsertRoutesAsyncTsk extends AsyncTask<Routes, Void, Void> {

        private RoutesDao routesDao;

        public InsertRoutesAsyncTsk(RoutesDao routesDao) {
            this.routesDao = routesDao;
        }

        @Override
        protected Void doInBackground(Routes... routes) {
            routesDao.insert(routes[0]);
            return null;
        }
    }

}
