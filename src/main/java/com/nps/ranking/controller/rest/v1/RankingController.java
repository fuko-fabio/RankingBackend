package com.nps.ranking.controller.rest.v1;

import com.nps.ranking.model.dto.OverallRatingDTO;
import com.nps.ranking.service.api.IOverallRatingsService;
import com.nps.ranking.service.impl.OverallRatingsService;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Norbert Pabian on 17.03.17
 */
@Api(name = "Ranking service", description = "Methods to get ranking", visibility = ApiVisibility.PUBLIC, stage = ApiStage.RC)
@ApiVersion(since = "1.0")
@ApiAuthNone
@RestController
@RequestMapping("/v1/ranking")
public class RankingController {

    private final IOverallRatingsService overallRatingService;

    @Autowired
    public RankingController(OverallRatingsService overallRatingService) {
        this.overallRatingService = overallRatingService;
    }

    @ApiMethod(description = "Returns pageable items ranking")
    @RequestMapping(method = RequestMethod.GET)
    public @ApiResponseObject Page<OverallRatingDTO> get(
            @ApiQueryParam(description = "Page number")
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @ApiQueryParam(description = "Page size")
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @ApiQueryParam(description = "Rater ID")
            @RequestParam(value = "raterId", required = false) String raterId) {
        return overallRatingService.getPaginated(page, size, raterId);
    }
}
