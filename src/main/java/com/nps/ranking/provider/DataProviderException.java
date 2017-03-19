package com.nps.ranking.provider;

import java.io.IOException;

/**
 * Created by Norbert Pabian on 19.03.17
 */
public class DataProviderException extends Exception {
    public DataProviderException(String message) {
        super(message);
    }

    public DataProviderException(Throwable e) {
        super(e);
    }

    public DataProviderException(String message, Throwable e) {
        super(message, e);
    }
}
