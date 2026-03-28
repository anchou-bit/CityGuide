package com.example.cityguide.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "attractions")
public class Attraction {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String city;
    private String address;
    private double latitude;
    private double longitude;
    private String imagePath;
    private long createdAt;

    public Attraction(String name, String city, String address,
                      double latitude, double longitude, String imagePath) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagePath = imagePath;
        this.createdAt = System.currentTimeMillis();
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}