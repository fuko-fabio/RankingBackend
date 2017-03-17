package com.nps.ranking.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Norbert Pabian on 17.03.17
 */
@Entity
@Table(name = "overall_ratings")
public class OverallRating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    @NotNull
    private String itemId;

    @Column
    @NotNull
    private float rating;

    @Column
    @NotNull
    private int ratingsCount;

    public OverallRating() {
    }

    public OverallRating(String itemId, float rating, int ratingsCount) {
        this.rating = rating;
        this.ratingsCount = ratingsCount;
        this.itemId = itemId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }
}
