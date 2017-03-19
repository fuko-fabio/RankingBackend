package com.nps.ranking.converters;

import com.nps.ranking.model.dto.RatingDTO;
import com.nps.ranking.model.entity.Rating;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * Created by Norbert Pabian on 16.03.17
 */
@Component
public class RatingConverter {

    private final ModelMapper modelMapper;

    public RatingConverter() {
        this.modelMapper = new ModelMapper();
    }

    @NotNull
    public Rating toEntity(@NotNull RatingDTO ratingDto) {
        return modelMapper.map(ratingDto, Rating.class);
    }

    @Nullable
    public RatingDTO toDto(@Nullable Rating rating) {
        return rating != null ? modelMapper.map(rating, RatingDTO.class) : null;
    }
}
