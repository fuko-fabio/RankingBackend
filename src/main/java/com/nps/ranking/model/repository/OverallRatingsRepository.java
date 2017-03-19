package com.nps.ranking.model.repository;

import com.nps.ranking.model.entity.OverallRating;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Norbert Pabian on 17.03.17
 */
public interface OverallRatingsRepository extends PagingAndSortingRepository<OverallRating, Long> {

    /**
     * Returns list of overall ratings for specified item ID. Maximum one item
     *
     * @param itemId the item ID.
     */
    OverallRating findByItemId(String itemId);
}
