package com.nps.ranking.provider.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nps.ranking.provider.DataProviderException;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Norbert Pabian on 18.03.17
 */
public class CachedRestDataProvider extends BaseRestDataProvider {
    private static final Logger LOGGER = Logger.getLogger(CachedRestDataProvider.class);
    private static final String PROPERTIES_FILE = "cached-rest-data-provider.properties";
    private static final String ID_PLACEHOLDER = "%{id}";
    private static final String URL_PROPERTY = "url";

    private final LoadingCache<String, Map> items;

    public CachedRestDataProvider() throws DataProviderException {
        Properties props = loadProperties();

        this.items = CacheBuilder.newBuilder()
                .maximumSize(Integer.valueOf(props.getProperty("cacheSize")))
                .expireAfterAccess(Long.valueOf(props.getProperty("cacheExpireTime")), TimeUnit.valueOf(props.getProperty("cacheExpireTimeUnit")))
                .build(new CacheLoader<String, Map>() {
                    @Override
                    public Map load(String itemId) throws Exception {
                        LOGGER.debug("Updating cached data for item ID: " + itemId);
                        return requestData(new URL(props.getProperty(URL_PROPERTY).replace(ID_PLACEHOLDER, itemId)));
                    }
                });
    }

    @Override
    public Object getData(String itemId) {
        try {
            return items.get(itemId);
        } catch (ExecutionException e) {
            LOGGER.error("Unable to get item data.", e);
        }
        return null;
    }

    @Override
    protected String getPropertiesFileName() {
        return PROPERTIES_FILE;
    }
}
