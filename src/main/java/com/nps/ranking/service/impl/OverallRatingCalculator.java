package com.nps.ranking.service.impl;

import com.nps.ranking.model.entity.OverallRating;
import com.nps.ranking.model.entity.Rating;
import com.nps.ranking.model.repository.OverallRatingsRepository;
import com.nps.ranking.model.repository.RatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;
import java.util.function.ToIntFunction;

/**
 * Created by Norbert Pabian on 17.03.17
 */
@Service
public class OverallRatingCalculator {

    private final RatingsRepository ratingsRepository;
    private final OverallRatingsRepository overallRatingsRepository;

    @Autowired
    public OverallRatingCalculator(RatingsRepository ratingsRepository,
                                   OverallRatingsRepository overallRatingsRepository) {
        this.ratingsRepository = ratingsRepository;
        this.overallRatingsRepository = overallRatingsRepository;
    }

    void calculate(String itemId) {
        List<Rating> ratings = ratingsRepository.findByItemId(itemId);
        OptionalDouble average = ratings.stream().mapToInt(new ToIntFunction<Rating>() {
            @Override
            public int applyAsInt(Rating rating) {
                return rating.getValue();
            }
        }).average();

        OverallRating or = null;
        List<OverallRating> list = overallRatingsRepository.findByItemId(itemId);
        if (list.isEmpty()) {
            or = new OverallRating();
            or.setItemId(itemId);
        } else if (list.size() ==1) {
            or = list.get(0);
        } else {
            throw new IllegalStateException("More than one overall rating found for item ID: " + itemId);
        }
        or.setRatingsCount(ratings.size());
        or.setRating((float) average.getAsDouble());
    }
}
