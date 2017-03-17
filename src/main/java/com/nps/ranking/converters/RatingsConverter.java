package com.nps.ranking.converters;

import com.nps.ranking.model.dto.RatingDTO;
import com.nps.ranking.model.entity.Rating;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Created by Norbert Pabian on 16.03.17
 */
@Component
public class RatingsConverter {

    private final ModelMapper modelMapper;

    public RatingsConverter() {
        this.modelMapper = new ModelMapper();
    }

    public Rating toEntity(RatingDTO ratingDto) {
        return modelMapper.map(ratingDto, Rating.class);
    }

    public RatingDTO toDto(Rating rating) {
        return modelMapper.map(rating, RatingDTO.class);
    }
}
