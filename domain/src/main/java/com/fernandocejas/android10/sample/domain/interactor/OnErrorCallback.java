package com.fernandocejas.android10.sample.domain.interactor;

/**
 * Callback invoked when an error happens inside of a use case.
 */
public interface OnErrorCallback<T extends Error> {
    /**
     * Method called when an error has occurred while executing a use case.
     *
     * @param error Error thrown by the use case
     * @return true if the error has been consumed, meaning it won't be notified to
     * other registered callbacks
     */
    boolean onError(T error);
}

