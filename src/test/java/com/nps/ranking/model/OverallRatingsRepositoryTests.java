package com.nps.ranking.model;

import com.nps.ranking.model.entity.OverallRating;
import com.nps.ranking.model.repository.OverallRatingsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Norbert Pabian on 17.03.17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class OverallRatingsRepositoryTests {

    private static final String ITEM_ID = "item_ID";

    @Autowired
    private OverallRatingsRepository overallRatingsRepository;

    @After
    public void cleanUp() {
        overallRatingsRepository.deleteAll();
    }

    @Test
    public void createOverallRatingTest() throws Exception {
        OverallRating or = aOverallRating(5.5f ,120);

        assertThat(or).isNotNull();
        assertThat(or.getItemId()).isEqualTo(ITEM_ID);
        assertThat(or.getRating()).isEqualTo(5.5f);
        assertThat(or.getRatingsCount()).isEqualTo(120);
    }

    @Test
    public void updateOverallRatingTest() throws Exception {
        OverallRating or = aOverallRating(4.0f ,20);

        assertThat(or.getItemId()).isEqualTo(ITEM_ID);
        assertThat(or.getRating()).isEqualTo(4.0f);
        assertThat(or.getRatingsCount()).isEqualTo(20);

        or.setRating(4.5f);
        or.setRatingsCount(100);
        or = overallRatingsRepository.save(or);

        assertThat(or.getItemId()).isEqualTo(ITEM_ID);
        assertThat(or.getRating()).isEqualTo(4.5f);
        assertThat(or.getRatingsCount()).isEqualTo(100);
    }

    @Test
    public void findOverallRatingTest() throws Exception {
        for(int i = 10; i < 20; i++) {
            aOverallRating("ID" + i, 3.5f, i);
        }
        Page<OverallRating> results = overallRatingsRepository.findAll(new PageRequest(1, 20));

        assertThat(results.getTotalElements()).isEqualTo(10);

        OverallRating or = overallRatingsRepository.findByItemId("ID10");
        assertThat(or).isNotNull();
        assertThat(or.getRating()).isEqualTo(3.5f);

        or = overallRatingsRepository.findByItemId("notExisting");
        assertThat(or).isNull();
    }

    @Test
    public void deleteOverallRatingTest() throws Exception {
        OverallRating or = aOverallRating(3.5f ,10);

        assertThat(or).isNotNull();

        overallRatingsRepository.delete(or.getId());
        or = overallRatingsRepository.findOne(or.getId());

        assertThat(or).isNull();
    }

    private OverallRating aOverallRating(float rating, int count) {
        return aOverallRating(ITEM_ID, rating, count);
    }

    private OverallRating aOverallRating(String itemId, float rating, int count) {
        return overallRatingsRepository.save(new OverallRating(itemId, rating, count));
    }
}
