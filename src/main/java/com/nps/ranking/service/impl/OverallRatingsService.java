package com.nps.ranking.service.impl;

import com.nps.ranking.Configuration;
import com.nps.ranking.model.dto.OverallRatingDTO;
import com.nps.ranking.model.entity.OverallRating;
import com.nps.ranking.model.repository.OverallRatingsRepository;
import com.nps.ranking.model.repository.RatingsRepository;
import com.nps.ranking.service.api.IItemDataProvider;
import com.nps.ranking.service.api.IOverallRatingsService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final IItemDataProvider itemDataProvider;
    private final Configuration configuration;
    private final ModelMapper modelMapper;
    private final ExecutorService executor;

    @Autowired
    public OverallRatingsService(RatingsRepository ratingsRepository,
                                 OverallRatingsRepository overallRatingsRepository,
                                 Configuration configuration) {
        this.ratingsRepository = ratingsRepository;
        this.overallRatingsRepository = overallRatingsRepository;
        this.configuration = configuration;
        this.itemDataProvider = getItemDataProvider();
        this.modelMapper = new ModelMapper();
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public Page<OverallRatingDTO> getPaginated(int page, int size, String raterId) {
        LOGGER.debug(String.format("Get overall ratings page: %s size: %s", page, size));

        Page<OverallRatingDTO> list = overallRatingsRepository.findAll(new PageRequest(page, size)).map((overallRating) -> {
            OverallRatingDTO dto = modelMapper.map(overallRating, OverallRatingDTO.class);
            if (raterId != null) {
                LOGGER.debug(String.format("Merging 'rated' property for rater ID: %s", raterId));
                dto.setRated(isRatedBy(overallRating.getItemId(), raterId));
            }
            if (configuration.isExternalItemDataEnabled()) {
                LOGGER.debug("Fetching item data for list entry.");
                dto.setItemData(itemDataProvider.getData(dto.getItemId()));
            }
            return dto;
        });
        return list;
    }

    @Override
    public void onRatingChanged(final String itemId) {
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

    private boolean isRatedBy(String itemId, String raterId) {
        return ratingsRepository.findByItemIdAndRaterId(itemId, raterId) != null;
    }

    private IItemDataProvider getItemDataProvider() {
        try {
            Class<?> c = Class.forName(configuration.getExternalItemDataProvider());
            return (IItemDataProvider) c.newInstance();
        } catch(Exception e){
            LOGGER.error("Unable to instantiate item data provider of class: " + configuration.getExternalItemDataProvider());
            throw new IllegalStateException(e);
        }
    }
}
