package com.nps.ranking.model.repository;

import com.nps.ranking.model.entity.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

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
     * Returns count of ratings and average ratings value
     *
     * @param raterId the item ID.
     */
    List<Rating> findByRaterId(String raterId);

    /**
     * Returns rating for specified item ID and rater ID
     *
     * @param itemId the item ID.
     * @param raterId the item ID.
     */
    Rating findByItemIdAndRaterId(String itemId, String raterId);

    /**
     * Returns ratings count and rating average value for specified item ID
     *
     * @param itemId the item ID.
     */
    @Query("SELECT COUNT(r.id) AS count, AVG(r.value) AS average FROM Rating r WHERE r.itemId=:itemId")
    Map<String, Object> getOverallRatingData(@Param("itemId") String itemId);
}
