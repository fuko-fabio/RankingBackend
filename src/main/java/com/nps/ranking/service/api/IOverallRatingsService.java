package com.nps.ranking.service.api;

import com.nps.ranking.model.dto.OverallRatingDTO;
import org.springframework.data.domain.Page;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * Created by Norbert Pabian on 17.03.17
 */
public interface IOverallRatingsService {

    /**
     * Returns page of overall ratings objects.
     *
     * @param page the page number.
     * @param size the page size.
     * @param raterId the rater ID (optional). To check if item was rated by this user.
     * @return page of overall ratings objects
     */
    @NotNull
    Page<OverallRatingDTO> getPaginated(int page, int size, @Nullable String raterId);

    /**
     * Returns single overall ratings for specified item ID.
     *
     * @param itemId the item ID.
     * @param raterId the rater ID (optional). To check if item was rated by this user.
     * @return overall rating model or null
     */
    @Nullable
    OverallRatingDTO getOverallRatingForItem(@NotNull String itemId, @Nullable String raterId);


    /**
     * Updates overall rating ranking values (average rating and ratings count).
     *
     * @param itemId the item ID.
     */
    void updateOverallRating(@NotNull String itemId);

}
