package com.nps.ranking.service.api;

import com.nps.ranking.model.dto.OverallRatingDTO;
import org.springframework.data.domain.Page;

/**
 * Created by Norbert Pabian on 17.03.17
 */
public interface IOverallRatingsService {
    Page<OverallRatingDTO> getPaginated(int page, int size);
}
