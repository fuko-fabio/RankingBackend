package com.nps.ranking.model;

import com.nps.ranking.model.entity.Rating;
import com.nps.ranking.model.repository.RatingsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Norbert Pabian on 16.03.17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RatingRepositoryTests {

    private static final String RATER_ID = "raterId";
    private static final String ITEM_ID = "itemId";

    @Autowired
    private RatingsRepository ratingRepository;

    @Test
    public void reateRatingTest() throws Exception {
        Rating rating = ratingRepository.save(aRating(5));

        assertThat(rating).isNotNull();
        assertThat(rating.getItemId()).isEqualTo(ITEM_ID);
        assertThat(rating.getRaterId()).isEqualTo(RATER_ID);
        assertThat(rating.getValue()).isEqualTo(5);
    }

    @Test
    public void updateRatingTest() throws Exception {
        Rating rating = ratingRepository.save(aRating(3));

        assertThat(rating.getItemId()).isEqualTo(ITEM_ID);
        assertThat(rating.getRaterId()).isEqualTo(RATER_ID);
        assertThat(rating.getValue()).isEqualTo(3);

        rating.setValue(8);
        rating = ratingRepository.save(aRating(8));

        assertThat(rating.getItemId()).isEqualTo(ITEM_ID);
        assertThat(rating.getRaterId()).isEqualTo(RATER_ID);
        assertThat(rating.getValue()).isEqualTo(8);
    }

    @Test
    public void findRatingTest() throws Exception {
        String itemId = "myItem";
        String raterId = "myRater";
        Rating rating = ratingRepository.save(aRating(itemId, 1));
        rating = ratingRepository.findOne(rating.getId());

        assertThat(rating.getItemId()).isEqualTo(itemId);

        List<Rating> ratings = ratingRepository.findByItemId(itemId);

        assertThat(ratings.size()).isEqualTo(1);
        assertThat(ratings.get(0).getItemId()).isEqualTo(itemId);

        ratingRepository.save(aRating(itemId, 1, raterId));
        ratings = ratingRepository.findByRaterId(raterId);

        assertThat(ratings.size()).isEqualTo(1);
        assertThat(ratings.get(0).getRaterId()).isEqualTo(raterId);
    }

    @Test
    public void deleteRatingTest() throws Exception {
        ratingRepository.save(aRating("toDelete", 1));
        List<Rating> ratings = ratingRepository.findByItemId("toDelete");

        assertThat(ratings.size()).isEqualTo(1);

        ratingRepository.delete(ratings.get(0).getId());
        ratings = ratingRepository.findByItemId("toDelete");
        assertThat(ratings.size()).isEqualTo(0);
    }

    private Rating aRating(int value) {
        return aRating(ITEM_ID, value);
    }

    private Rating aRating(String itemId, int value) {
        return aRating(itemId, value, RATER_ID);
    }

    private Rating aRating(String itemId, int value, String raterId) {
        return new Rating(itemId, value, raterId);
    }
}
