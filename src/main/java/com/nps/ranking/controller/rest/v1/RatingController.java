package com.nps.ranking.controller.rest.v1;

import com.nps.ranking.model.dto.RatingDTO;
import com.nps.ranking.service.api.IRatingService;
import com.nps.ranking.service.impl.RatingsService;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Norbert Pabian on 16.03.17
 */
@Api(name = "Rating service", description = "Methods for managing single rating", visibility = ApiVisibility.PUBLIC, stage = ApiStage.RC)
@ApiVersion(since = "1.0")
@ApiAuthNone
@RestController
@RequestMapping("/v1/rating")
public class RatingController {

    private IRatingService ratingService;

    @Autowired
    public RatingController(RatingsService ratingService) {
        this.ratingService = ratingService;
    }

    @ApiMethod(description = "Gets rating object by item ID and rater ID")
    @RequestMapping(method = RequestMethod.GET, params = {"itemId", "raterId"})
    public @ApiResponseObject RatingDTO find(
            @ApiQueryParam(description = "Item ID", name = "itemId")
            @RequestParam String itemId,
            @ApiQueryParam(description = "Rater ID", name = "raterId")
            @RequestParam String raterId) {
        return ratingService.getByItemIdAndRaterId(itemId, raterId);
    }

    @ApiMethod(description = "Gets rating object by rating ID ")
    @RequestMapping(value = "/{ratingId}", method = RequestMethod.GET)
    public @ApiResponseObject RatingDTO getById(
            @ApiPathParam(description = "Rating ID", name = "ratingId")
            @PathVariable Long ratingId) {
        return ratingService.getById(ratingId);
    }

    @ApiMethod(description = "Creates rating object")
    @RequestMapping(method = RequestMethod.POST)
    public void create(
            @ApiBodyObject
            @RequestBody RatingDTO rating) {
        ratingService.create(rating);
    }

    @ApiMethod(description = "Updates rating object")
    @RequestMapping(value = "/{ratingId}", method = RequestMethod.PUT)
    public void update(
            @ApiBodyObject
            @RequestBody RatingDTO rating) {
        ratingService.update(rating);
    }

    @ApiMethod(description = "Deletes rating object")
    @RequestMapping(value = "/{ratingId}", method = RequestMethod.DELETE)
    public void delete(
            @ApiPathParam(description = "Rating ID", name = "ratingId")
            @PathVariable Long ratingId) {
        ratingService.delete(ratingId);
    }
}

