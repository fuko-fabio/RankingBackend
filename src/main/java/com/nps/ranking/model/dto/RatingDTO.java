package com.nps.ranking.model.dto;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

/**
 * Created by Norbert Pabian on 16.03.17
 */
@ApiObject(name = "Rating")
public class RatingDTO {

    @ApiObjectField(description = "Rating ID")
    private Long id;

    @ApiObjectField(description = "Item ID", required = true)
    private String itemId;

    @ApiObjectField(description = "Rater ID")
    private String raterId;

    @ApiObjectField(description = "Rating value", required = true)
    private Integer value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
