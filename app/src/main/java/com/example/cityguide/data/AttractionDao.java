package com.example.cityguide.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cityguide.data.models.Attraction;

import java.util.List;

@Dao
public interface AttractionDao {
    @Insert
    long insert(Attraction attraction);

    @Update
    void update(Attraction attraction);

    @Delete
    void delete(Attraction attraction);

    @Query("SELECT * FROM attractions ORDER BY createdAt DESC")
    LiveData<List<Attraction>> getAllAttractions();

    @Query("SELECT * FROM attractions WHERE id = :id")
    Attraction getAttractionById(int id);
}