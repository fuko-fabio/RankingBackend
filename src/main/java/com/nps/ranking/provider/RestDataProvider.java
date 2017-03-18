package com.nps.ranking.provider;

import com.nps.ranking.Application;
import com.nps.ranking.service.api.IItemDataProvider;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by Norbert Pabian on 18.03.17
 */
public class RestDataProvider implements IItemDataProvider {
    private static final Logger LOGGER = Logger.getLogger(RestDataProvider.class);
    private static final String PROPERTIES_FILE = "rest-data-provider.properties";
    private static final String ID_PLACEHOLDER = "%{id}";
    private static final String URL_PROPERTY = "url";

    @Override
    public Object getData(String itemId) {
        String urlProp;
        try {
            Properties props = loadProperties();
            urlProp = props.getProperty(URL_PROPERTY);
            if (urlProp == null || urlProp.isEmpty()) {
                LOGGER.error("Missing irl property in configuration file: " + PROPERTIES_FILE);
                return null;
            }
        } catch (IOException e) {
            LOGGER.error("Unable to load configuration properties file.", e);
            return null;
        }
        try {
            URL url = new URL(urlProp.replace(ID_PLACEHOLDER, itemId));
            LOGGER.debug("Reading item data from: " + url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                LOGGER.error("Unable to get data from: " + url.toString());
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder body = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                body.append(output);
            }
            conn.disconnect();
            return new JSONObject(body.toString()).toMap();
        } catch (MalformedURLException e) {
            LOGGER.error("Unable to read url from configuration properties file.", e);
        } catch (ProtocolException e) {
            LOGGER.error("Invalid protocol", e);
        } catch (IOException e) {
            LOGGER.error("Connection error.", e);
        }
        return null;
    }

    private Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(Application.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        return properties;
    }
}
