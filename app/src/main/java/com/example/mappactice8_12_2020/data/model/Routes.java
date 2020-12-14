package com.example.mappactice8_12_2020.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "routes")
public class Routes {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String routesLength;
    private String averageSpeed;
    private String timeSpent;

    public Routes(String routesLength, String averageSpeed, String timeSpent) {
        this.routesLength = routesLength;
        this.averageSpeed = averageSpeed;
        this.timeSpent = timeSpent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoutesLength() {
        return routesLength;
    }

    public void setRoutesLength(String routesLength) {
        this.routesLength = routesLength;
    }

    public String getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }
}
