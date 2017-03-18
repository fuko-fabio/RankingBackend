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
import java.util.Map;

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
        Rating rating = aRating(5);

        assertThat(rating).isNotNull();
        assertThat(rating.getItemId()).isEqualTo(ITEM_ID);
        assertThat(rating.getRaterId()).isEqualTo(RATER_ID);
        assertThat(rating.getValue()).isEqualTo(5);
    }

    @Test
    public void updateRatingTest() throws Exception {
        Rating rating = aRating(3);

        assertThat(rating.getItemId()).isEqualTo(ITEM_ID);
        assertThat(rating.getRaterId()).isEqualTo(RATER_ID);
        assertThat(rating.getValue()).isEqualTo(3);

        rating.setValue(8);
        rating = aRating(8);

        assertThat(rating.getItemId()).isEqualTo(ITEM_ID);
        assertThat(rating.getRaterId()).isEqualTo(RATER_ID);
        assertThat(rating.getValue()).isEqualTo(8);
    }

    @Test
    public void findRatingTest() throws Exception {
        String itemId = "myItem";
        String raterId = "myRater";
        Rating rating = aRating(itemId, 1);
        rating = ratingRepository.findOne(rating.getId());

        assertThat(rating.getItemId()).isEqualTo(itemId);

        List<Rating> ratings = ratingRepository.findByItemId(itemId);

        assertThat(ratings.size()).isEqualTo(1);
        assertThat(ratings.get(0).getItemId()).isEqualTo(itemId);

        aRating(itemId, 1, raterId);
        ratings = ratingRepository.findByRaterId(raterId);

        assertThat(ratings.size()).isEqualTo(1);
        assertThat(ratings.get(0).getRaterId()).isEqualTo(raterId);

        rating = ratingRepository.findByItemIdAndRaterId(itemId, raterId);
        assertThat(rating).isNotNull();
        assertThat(rating.getItemId()).isEqualTo(itemId);

        rating = ratingRepository.findByItemIdAndRaterId(itemId, "notExisting");
        assertThat(rating).isNull();
    }

    @Test
    public void deleteRatingTest() throws Exception {
        aRating("toDelete", 1);
        List<Rating> ratings = ratingRepository.findByItemId("toDelete");

        assertThat(ratings.size()).isEqualTo(1);

        ratingRepository.delete(ratings.get(0).getId());
        ratings = ratingRepository.findByItemId("toDelete");
        assertThat(ratings.size()).isEqualTo(0);
    }

    @Test
    public void getOverallRatingDataTest() {
        aRating("rankingData", 6, "1");
        aRating("rankingData", 7, "2");
        aRating("rankingData", 3, "3");
        aRating("rankingData", 4, "4");

        Map<String, Object> result = ratingRepository.getOverallRatingData("rankingData");
        assertThat(result.isEmpty()).isFalse();
        assertThat((Long) result.get("count")).isEqualTo(4);
        assertThat((Double) result.get("average")).isEqualTo(5.0);
    }

    private Rating aRating(int value) {
        return aRating(ITEM_ID, value);
    }

    private Rating aRating(String itemId, int value) {
        return aRating(itemId, value, RATER_ID);
    }

    private Rating aRating(String itemId, int value, String raterId) {
        return ratingRepository.save(new Rating(itemId, value, raterId));
    }
}
