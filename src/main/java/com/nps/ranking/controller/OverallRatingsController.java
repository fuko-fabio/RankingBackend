package com.nps.ranking.controller;

import com.nps.ranking.model.dto.OverallRatingDTO;
import com.nps.ranking.service.api.IOverallRatingsService;
import com.nps.ranking.service.impl.OverallRatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Norbert Pabian on 17.03.17
 */
@RestController
@RequestMapping("/overalls")
public class OverallRatingsController {

    private final IOverallRatingsService overallRatingService;

    @Autowired
    public OverallRatingsController(OverallRatingsService overallRatingService) {
        this.overallRatingService = overallRatingService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<OverallRatingDTO> get(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return overallRatingService.getPaginated(page, size);
    }
}
