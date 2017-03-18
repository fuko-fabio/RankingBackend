package com.nps.ranking;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Norbert Pabian on 18.03.17
 */
@Component
@ConfigurationProperties(prefix="ranking")
public class Configuration {

    private boolean externalItemDataEnabled;
    private String externalItemDataProvider;

    public boolean isExternalItemDataEnabled() {
        return externalItemDataEnabled;
    }

    public void setExternalItemDataEnabled(boolean externalItemDataEnabled) {
        this.externalItemDataEnabled = externalItemDataEnabled;
    }

    public String getExternalItemDataProvider() {
        return externalItemDataProvider;
    }

    public void setExternalItemDataProvider(String externalItemDataProvider) {
        this.externalItemDataProvider = externalItemDataProvider;
    }
}
