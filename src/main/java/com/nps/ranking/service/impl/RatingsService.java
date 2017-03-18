package com.nps.ranking.service.impl;

import com.nps.ranking.converters.RatingsConverter;
import com.nps.ranking.model.dto.RatingDTO;
import com.nps.ranking.model.entity.Rating;
import com.nps.ranking.model.repository.RatingsRepository;
import com.nps.ranking.service.api.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Norbert Pabian on 16.03.17
 */
@Service
public class RatingsService implements IRatingService {

    private final RatingsRepository ratingsRepository;
    private final RatingsConverter ratingsConverter;
    private final OverallRatingsService overallRatingsService;

    @Autowired
    public RatingsService(RatingsRepository ratingsRepository,
                          RatingsConverter ratingsConverter,
                          OverallRatingsService overallRatingsService) {
        this.ratingsRepository = ratingsRepository;
        this.ratingsConverter = ratingsConverter;
        this.overallRatingsService = overallRatingsService;
    }

    @Override
    public RatingDTO getById(Long id) {
        return ratingsConverter.toDto(ratingsRepository.findOne(id));
    }

    @Override
    public RatingDTO getByItemIdAndRaterId(String itemId, String raterId) {
        return ratingsConverter.toDto(ratingsRepository.findByItemIdAndRaterId(itemId, raterId));
    }

    @Override
    public void delete(Long id) {
        Rating rating = ratingsRepository.findOne(id);
        ratingsRepository.delete(rating.getId());
        overallRatingsService.onRatingChanged(rating.getItemId());
    }

    @Override
    public RatingDTO update(RatingDTO rating) {
        RatingDTO result = ratingsConverter.toDto(ratingsRepository.save(ratingsConverter.toEntity(rating)));
        overallRatingsService.onRatingChanged(result.getItemId());
        return result;
    }

    @Override
    public RatingDTO create(RatingDTO rating) {
        RatingDTO result = ratingsConverter.toDto(ratingsRepository.save(ratingsConverter.toEntity(rating)));
        overallRatingsService.onRatingChanged(result.getItemId());
        return result;
    }
}
