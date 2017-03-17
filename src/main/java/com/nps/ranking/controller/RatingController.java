package com.nps.ranking.controller;

import com.nps.ranking.model.dto.RatingDTO;
import com.nps.ranking.service.api.IRatingService;
import com.nps.ranking.service.impl.RatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Norbert Pabian on 16.03.17
 */
@RestController
@RequestMapping("/rating")
public class RatingController {

    private IRatingService ratingService;

    @Autowired
    public RatingController(RatingsService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping(value = "/{ratingId}", method = RequestMethod.GET)
    public RatingDTO getById(@PathVariable Long ratingId) {
        return ratingService.getById(ratingId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody RatingDTO rating) {
        ratingService.create(rating);
    }

    @RequestMapping(value = "/{ratingId}", method = RequestMethod.PUT)
    public void update(@RequestBody RatingDTO rating) {
        ratingService.update(rating);
    }

    @RequestMapping(value = "/{ratingId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long ratingId) {
        ratingService.delete(ratingId);
    }


}

