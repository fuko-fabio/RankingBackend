package com.nps.ranking.service;

import com.nps.ranking.model.repository.OverallRatingsRepository;
import com.nps.ranking.model.repository.RatingsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Norbert Pabian on 17.03.17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class OverallRatingsCalculatorTests {

    @Mock
    private OverallRatingsRepository overallRatingsRepository;

    @Mock
    private RatingsRepository ratingsRepository;

    @Test
    public void testCalculate() {

    }
}
