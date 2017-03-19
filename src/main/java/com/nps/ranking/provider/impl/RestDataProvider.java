package com.nps.ranking.provider.impl;

import com.nps.ranking.provider.DataProviderException;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by Norbert Pabian on 18.03.17
 */
public class RestDataProvider extends BaseRestDataProvider {
    private static final Logger LOGGER = Logger.getLogger(RestDataProvider.class);
    private static final String PROPERTIES_FILE = "rest-data-provider.properties";

    private final String serviceUrlTemplate;

    public RestDataProvider() throws DataProviderException {
        Properties props = loadProperties();
        serviceUrlTemplate = props.getProperty(URL_PROPERTY);
        if (serviceUrlTemplate == null || serviceUrlTemplate.isEmpty()) {
            throw new DataProviderException("Missing url property in configuration file: " + PROPERTIES_FILE);
        }
    }

    @Override
    public Object getData(String itemId) {
        try {
            return requestData(new URL(serviceUrlTemplate.replace(ID_PLACEHOLDER, itemId)));
        } catch (MalformedURLException e) {
            LOGGER.error("Unable to read url from configuration properties file.", e);
        }
        return null;
    }

    @Override
    protected String getPropertiesFileName() {
        return PROPERTIES_FILE;
    }
}
