package com.nps.ranking.models;

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

    @NotNull
    private long itemId;
    private short value;

    public Rating() {
    }

    public Rating(long itemId, short value) {
        this.itemId = itemId;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }


}
