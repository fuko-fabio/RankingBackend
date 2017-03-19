package com.nps.ranking.service.impl;

import com.nps.ranking.converters.OverallRatingConverter;
import com.nps.ranking.model.dto.OverallRatingDTO;
import com.nps.ranking.model.entity.OverallRating;
import com.nps.ranking.model.repository.OverallRatingsRepository;
import com.nps.ranking.model.repository.RatingsRepository;
import com.nps.ranking.service.api.IOverallRatingsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Norbert Pabian on 17.03.17
 */
@Service
public class OverallRatingsService implements IOverallRatingsService {
    private static final Logger LOGGER = Logger.getLogger(OverallRatingsService.class);
    private final RatingsRepository ratingsRepository;
    private final OverallRatingsRepository overallRatingsRepository;
    private final OverallRatingConverter converter;
    private final ExecutorService executor;

    @Autowired
    public OverallRatingsService(RatingsRepository ratingsRepository,
                                 OverallRatingsRepository overallRatingsRepository,
                                 OverallRatingConverter converter) {
        this.ratingsRepository = ratingsRepository;
        this.overallRatingsRepository = overallRatingsRepository;
        this.converter = converter;
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public Page<OverallRatingDTO> getPaginated(int page, int size, String raterId) {
        LOGGER.debug(String.format("Get overall ratings page: %s size: %s", page, size));
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "rating"));
        return overallRatingsRepository.findAll(pageRequest).map((overallRating) -> converter.toDto(overallRating, raterId));
    }

    @Override
    public void updateOverallRating(final String itemId) {
        executor.submit(() -> {
            LOGGER.info("Creating or updating overall rating for item ID: " + itemId);
            Map<String, Object> result = ratingsRepository.getOverallRatingData(itemId);
            OverallRating or = overallRatingsRepository.findByItemId(itemId);
            if (or == null) {
                or = new OverallRating();
                or.setItemId(itemId);
            }
            or.setRating(((Double) result.get("average")).floatValue());
            or.setRatingsCount(((Long) result.get("count")).intValue());

            LOGGER.debug(String.format("Saving overall rating for item ID: %s Rating: %s Ratings count: %s ",
                    itemId, or.getRating(), or.getRatingsCount()));
            overallRatingsRepository.save(or);
        });
    }

    @Override
    public OverallRatingDTO getOverallRatingForItem(String itemId, String raterId) {
        return converter.toDto(overallRatingsRepository.findByItemId(itemId), raterId);
    }
}
