package com.nps.ranking.model.entity;

import javax.validation.constraints.NotNull;

import javax.persistence.*;

/**
 * Created by Norbert Pabian on 16.03.17
 */
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @NotNull
    private String itemId;

    @Column
    private String raterId;

    @Column
    @NotNull
    private int value;

    public Rating() {
    }

    public Rating(String itemId, int value, String raterId) {
        this.itemId = itemId;
        this.value = value;
        this.raterId = raterId;
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

    public String getRaterId() {
        return raterId;
    }

    public void setRaterId(String raterId) {
        this.raterId = raterId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
