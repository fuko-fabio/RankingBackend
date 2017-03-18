package com.nps.ranking.provider;

import com.github.javafaker.Faker;
import com.nps.ranking.service.api.IItemDataProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Norbert Pabian on 18.03.17
 */
public class FakeDataProvider implements IItemDataProvider {

    private final Faker faker;

    public FakeDataProvider() {
        this.faker = new Faker();
    }

    @Override
    public Object getData(String itemId) {
        Map<String, Object> item = new HashMap<>();
        item.put("title", faker.book().title());
        item.put("author", faker.book().author());
        item.put("genre", faker.book().genre());
        item.put("publisher", faker.book().publisher());
        item.put("cover", faker.internet().image());
        return item;
    }
}
