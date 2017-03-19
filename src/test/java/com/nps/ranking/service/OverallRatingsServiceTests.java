package com.nps.ranking.service;

import com.nps.ranking.Configuration;
import com.nps.ranking.converters.OverallRatingConverter;
import com.nps.ranking.model.dto.OverallRatingDTO;
import com.nps.ranking.model.entity.OverallRating;
import com.nps.ranking.model.entity.Rating;
import com.nps.ranking.model.repository.OverallRatingsRepository;
import com.nps.ranking.model.repository.RatingsRepository;
import com.nps.ranking.service.impl.OverallRatingsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Norbert Pabian on 17.03.17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class OverallRatingsServiceTests {

    @Mock
    private OverallRatingsRepository overallRatingsRepository;

    @Mock
    private RatingsRepository ratingsRepository;

    @Mock
    private Configuration config;

    @Before
    public void setUp() {
        when(config.getExternalItemDataProvider()).thenReturn("com.nps.ranking.provider.impl.FakeDataProvider");
    }

    @Test
    public void getOverallRatingForItemTest() {
        when(overallRatingsRepository.findByItemId(anyString())).thenReturn(null);
        assertThat(getService().getOverallRatingForItem("id", "rid")).isNull();

        when(overallRatingsRepository.findByItemId(anyString())).thenReturn(aOverallRating());
        when(config.isExternalItemDataEnabled()).thenReturn(false);
        OverallRatingDTO dto = getService().getOverallRatingForItem("id", null);

        assertThat(dto).isNotNull();
        assertThat(dto.getItemData()).isNull();
        assertThat(dto.getRated()).isNull();

        when(config.isExternalItemDataEnabled()).thenReturn(true);
        when(ratingsRepository.findByItemIdAndRaterId("id", "rid")).thenReturn(new Rating());
        dto = getService().getOverallRatingForItem("id", "rid");

        assertThat(dto).isNotNull();
        assertThat(dto.getRated()).isTrue();
        assertThat(dto.getItemData()).isNotNull();
    }

    private OverallRatingsService getService() {
        return new OverallRatingsService(
                ratingsRepository,
                overallRatingsRepository,
                new OverallRatingConverter(config, ratingsRepository));
    }

    private OverallRating aOverallRating() {
        return new OverallRating("id", 4.5f, 6);
    }
}
