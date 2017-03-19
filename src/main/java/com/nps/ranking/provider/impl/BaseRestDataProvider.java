package com.nps.ranking.provider.impl;

import com.nps.ranking.provider.api.ItemDataProvider;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Norbert Pabian on 19.03.17
 */
abstract class BaseRestDataProvider extends ItemDataProvider {
    private static final Logger LOGGER = Logger.getLogger(BaseRestDataProvider.class);
    static final String ID_PLACEHOLDER = "%{id}";
    static final String URL_PROPERTY = "url";

    @NotNull
    Map requestData(URL url) {
        Map result = null;
        try {
            LOGGER.debug("Reading item data from: " + url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                LOGGER.error("Unable to get data from: " + url.toString());
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder body = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    body.append(output);
                }
                conn.disconnect();
                result = new JSONObject(body.toString()).toMap();
            }
        } catch (ProtocolException e) {
            LOGGER.error("Invalid protocol", e);
        } catch (IOException e) {
            LOGGER.error("Connection error.", e);
        }
        return result != null ? result : new HashMap<>();
    }
}
