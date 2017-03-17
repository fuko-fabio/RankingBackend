package com.nps.ranking.service.impl;

import com.nps.ranking.model.dto.OverallRatingDTO;
import com.nps.ranking.model.entity.OverallRating;
import com.nps.ranking.model.repository.OverallRatingsRepository;
import com.nps.ranking.service.api.IOverallRatingsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by Norbert Pabian on 17.03.17
 */
@Service
public class OverallRatingsService implements IOverallRatingsService {

    private final OverallRatingsRepository overallRatingsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OverallRatingsService(OverallRatingsRepository overallRatingsRepository) {
        this.overallRatingsRepository = overallRatingsRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Page<OverallRatingDTO> getPaginated(int page, int size) {
        return overallRatingsRepository.findAll(new PageRequest(page, size)).map(new Converter<OverallRating, OverallRatingDTO>() {
            @Override
            public OverallRatingDTO convert(OverallRating overallRating) {
                return modelMapper.map(overallRating, OverallRatingDTO.class);
            }
        });
    }
}
