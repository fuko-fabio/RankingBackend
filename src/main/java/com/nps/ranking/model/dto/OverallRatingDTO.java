package com.nps.ranking.model.dto;

/**
 * Created by Norbert Pabian on 17.03.17
 */
public class OverallRatingDTO {

    private Long id;
    private Long itemId;
    private Float rating;
    private Integer ratingsCount;

    private Boolean rated;

    public OverallRatingDTO() {
    }

    public OverallRatingDTO(Long id, Long itemId, Float rating, Integer ratingsCount, Boolean rated) {
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

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
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

}
