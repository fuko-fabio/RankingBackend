package com.nps.ranking.provider.api;

import com.nps.ranking.Application;
import com.nps.ranking.provider.DataProviderException;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Norbert Pabian on 18.03.17
 */
public abstract class ItemDataProvider {
    abstract public Object getData(String itemId);
    abstract protected String getPropertiesFileName();

    @NotNull
    protected Properties loadProperties() throws DataProviderException {
        Properties properties = new Properties();
        try {
            properties.load(Application.class.getClassLoader().getResourceAsStream(getPropertiesFileName()));
        } catch (IOException e) {
            throw new DataProviderException("Unable to load configuration properties file.", e);
        }
        return properties;
    }
}
