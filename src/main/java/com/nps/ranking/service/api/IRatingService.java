package com.nps.ranking.service.api;

import com.nps.ranking.model.dto.RatingDTO;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/**
 * Created by Norbert Pabian on 16.03.17
 */
public interface IRatingService {

    /**
     * Returns single rating for specified ID.
     *
     * @param id the rating ID.
     * @return rating model or null
     */
    @Nullable
    RatingDTO getById(@NotNull Long id);

    /**
     * Returns single rating for specified item ID and rater ID.
     *
     * @param itemId the item ID.
     * @param raterId the rater ID.
     * @return rating model or null
     */
    @Nullable
    RatingDTO getByItemIdAndRaterId(@NotNull String itemId, @NotNull String raterId);

    /**
     * Updates rating model
     *
     * @param rating the rating model.
     * @return rating model or null
     */
    @NotNull
    RatingDTO update(@NotNull RatingDTO rating);

    /**
     * Creates rating model
     *
     * @param rating the rating model.
     * @return rating model or null
     */
    @NotNull
    RatingDTO create(@NotNull RatingDTO rating) throws RatingServiceException;

    /**
     * Deletes rating model
     *
     * @param id the rating id.
     */
    void delete(@NotNull Long id);
}
