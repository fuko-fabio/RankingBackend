package com.nps.ranking.converters;

import com.nps.ranking.Configuration;
import com.nps.ranking.model.dto.OverallRatingDTO;
import com.nps.ranking.model.entity.OverallRating;
import com.nps.ranking.model.repository.RatingsRepository;
import com.nps.ranking.provider.api.ItemDataProvider;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * Created by Norbert Pabian on 19.03.17
 */
@Component
public class OverallRatingConverter {
    private static final Logger LOGGER = Logger.getLogger(OverallRatingConverter.class);

    private final Configuration configuration;
    private final RatingsRepository ratingsRepository;
    private final ModelMapper modelMapper;
    private final ItemDataProvider itemDataProvider;

    @Autowired
    public OverallRatingConverter(Configuration configuration, RatingsRepository ratingsRepository) {
        this.configuration = configuration;
        this.ratingsRepository = ratingsRepository;
        this.modelMapper = new ModelMapper();
        this.itemDataProvider = getItemDataProvider();
    }

    @Nullable
    public OverallRatingDTO toDto(@Nullable OverallRating overallRating, @Nullable String raterId) {
        if (overallRating == null) {
            return null;
        }
        OverallRatingDTO dto = modelMapper.map(overallRating, OverallRatingDTO.class);

        if (raterId != null) {
            LOGGER.debug(String.format("Merging 'rated' property for rater ID: %s", raterId));
            dto.setRated(isRatedBy(dto.getItemId(), raterId));
        }
        if (configuration.isExternalItemDataEnabled()) {
            LOGGER.debug("Fetching item data for list item with ID: " + dto.getItemId());
            dto.setItemData(itemDataProvider.getData(dto.getItemId()));
        }
        return dto;
    }

    private boolean isRatedBy(String itemId, String raterId) {
        return ratingsRepository.findByItemIdAndRaterId(itemId, raterId) != null;
    }

    private ItemDataProvider getItemDataProvider() {
        if (!configuration.isExternalItemDataEnabled()) {
            return null;
        }
        try {
            Class<?> c = Class.forName(configuration.getExternalItemDataProvider());
            return (ItemDataProvider) c.newInstance();
        } catch(Exception e){
            LOGGER.error("Unable to instantiate item data provider of class: " + configuration.getExternalItemDataProvider());
            throw new IllegalStateException(e);
        }
    }
}
