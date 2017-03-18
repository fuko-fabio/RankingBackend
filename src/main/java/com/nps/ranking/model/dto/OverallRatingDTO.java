package com.nps.ranking.model.dto;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/**
 * Created by Norbert Pabian on 17.03.17
 */
@ApiObject(name = "OverallRating")
public class OverallRatingDTO {

    @ApiObjectField(description = "Rating ID")
    private Long id;

    @ApiObjectField(description = "Item ID")
    private String itemId;

    @ApiObjectField(description = "Rating value")
    private Float rating;

    @ApiObjectField(description = "Ratings total count")
    private Integer ratingsCount;

    @ApiObjectField(description = "Item was rated by current user/rater")
    private Boolean rated;

    @ApiObjectField(description = "Item data referenced by item ID. Can be empty. See configuration")
    private Object itemData;

    public OverallRatingDTO() {
    }

    public OverallRatingDTO(Long id, String itemId, Float rating, Integer ratingsCount, Boolean rated) {
        this.id = id;
        this.rating = rating;
        this.ratingsCount = ratingsCount;
        this.itemId = itemId;
        this.rated = rated;
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

    public Boolean getRated() {
        return rated;
    }

    public void setRated(Boolean rated) {
        this.rated = rated;
    }

    public Object getItemData() {
        return itemData;
    }

    public void setItemData(Object itemData) {
        this.itemData = itemData;
    }
}
