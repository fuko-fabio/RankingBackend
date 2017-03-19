package com.nps.ranking.provider;

/**
 * Created by Norbert Pabian on 19.03.17
 */
public class DataProviderException extends Exception {
    public DataProviderException(String message) {
        super(message);
    }

    public DataProviderException(String message, Throwable e) {
        super(message, e);
    }
}
