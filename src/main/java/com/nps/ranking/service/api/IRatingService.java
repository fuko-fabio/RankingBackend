package com.nps.ranking.service.api;

import com.nps.ranking.model.dto.RatingDTO;

/**
 * Created by Norbert Pabian on 16.03.17
 */
public interface IRatingService {

    RatingDTO getById(Long id);

    void delete(Long id);

    RatingDTO update(RatingDTO rating);

    RatingDTO create(RatingDTO rating);
}
