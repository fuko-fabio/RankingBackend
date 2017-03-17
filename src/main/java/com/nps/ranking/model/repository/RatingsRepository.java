package com.nps.ranking.model.repository;

import com.nps.ranking.model.entity.Rating;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Norbert Pabian on 16.03.17
 */
public interface RatingsRepository extends CrudRepository<Rating, Long> {

    /**
     * Returns list of ratings for specified item ID
     *
     * @param itemId the item ID.
     */
    List<Rating> findByItemId(String itemId);

    /**
     * Returns list of ratings for specified rater ID
     *
     * @param raterId the rater ID.
     */
    List<Rating> findByRaterId(String raterId);
}
