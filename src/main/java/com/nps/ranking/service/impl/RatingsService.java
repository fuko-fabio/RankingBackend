package com.nps.ranking.service.impl;

import com.nps.ranking.converters.RatingConverter;
import com.nps.ranking.model.dto.RatingDTO;
import com.nps.ranking.model.entity.Rating;
import com.nps.ranking.model.repository.RatingsRepository;
import com.nps.ranking.service.api.IRatingService;
import com.nps.ranking.service.api.RatingServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Norbert Pabian on 16.03.17
 */
@Service
public class RatingsService implements IRatingService {
    private static final Logger LOGGER = Logger.getLogger(RatingsService.class);
    private final RatingsRepository ratingsRepository;
    private final RatingConverter ratingConverter;
    private final OverallRatingsService overallRatingsService;

    @Autowired
    public RatingsService(RatingsRepository ratingsRepository,
                          RatingConverter ratingConverter,
                          OverallRatingsService overallRatingsService) {
        this.ratingsRepository = ratingsRepository;
        this.ratingConverter = ratingConverter;
        this.overallRatingsService = overallRatingsService;
    }

    @Override
    public RatingDTO getById(Long id) {
        return ratingConverter.toDto(ratingsRepository.findOne(id));
    }

    @Override
    public RatingDTO getByItemIdAndRaterId(String itemId, String raterId) {
        return ratingConverter.toDto(ratingsRepository.findByItemIdAndRaterId(itemId, raterId));
    }

    @Override
    public void delete(Long id) {
        Rating rating = ratingsRepository.findOne(id);
        ratingsRepository.delete(rating.getId());
        overallRatingsService.updateOverallRating(rating.getItemId());
    }

    @Override
    public RatingDTO update(RatingDTO rating) {
        RatingDTO result = ratingConverter.toDto(ratingsRepository.save(ratingConverter.toEntity(rating)));
        overallRatingsService.updateOverallRating(result.getItemId());
        return result;
    }

    @Override
    public RatingDTO create(RatingDTO rating) throws RatingServiceException {
        if (rating.getItemId() != null && rating.getRaterId() != null) {
            Rating model = ratingsRepository.findByItemIdAndRaterId(rating.getItemId(), rating.getRaterId());
            if (model != null) {
                String msg = String.format("Key pair item ID: %s rater ID: %s already exists",
                        rating.getItemId(), rating.getRaterId());
                LOGGER.error(msg);
                throw new RatingServiceException(msg);
            }
        }
        RatingDTO result = ratingConverter.toDto(ratingsRepository.save(ratingConverter.toEntity(rating)));
        overallRatingsService.updateOverallRating(result.getItemId());
        return result;
    }
}
