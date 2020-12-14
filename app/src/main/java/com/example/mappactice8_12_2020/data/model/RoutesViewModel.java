package com.example.mappactice8_12_2020.data.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mappactice8_12_2020.data.model.RoutesRepository;
import com.example.mappactice8_12_2020.data.model.Routes;

import java.util.List;

public class RoutesViewModel extends AndroidViewModel {

    private RoutesRepository routesRepository;
    private LiveData<List<Routes>> allRun;

    public RoutesViewModel(@NonNull Application application) {
        super(application);
        routesRepository = new RoutesRepository(application);
        allRun = routesRepository.getAllRun();
    }

    public void insert(Routes routes) {
        routesRepository.insert(routes);
    }

    public LiveData<List<Routes>> getAllRun() {
        return allRun;
    }
}
