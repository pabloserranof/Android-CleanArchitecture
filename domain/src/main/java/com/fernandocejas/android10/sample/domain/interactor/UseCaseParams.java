package com.fernandocejas.android10.sample.domain.interactor;

/**
 * Created by pablo on 05/04/2017.
 */

import java.lang.ref.WeakReference;

/**
 * The params value to execute with a use case.
 */
final class UseCaseParams {

    private final String useCaseName;
    private final Object[] args;
    private WeakReference<OnSuccessCallback> onSuccessCallback;
    private WeakReference<OnErrorCallback> onErrorCallback;

    UseCaseParams(String useCaseName, Object[] args,
                  OnSuccessCallback onSuccessCallback,
                  OnErrorCallback onErrorCallback) {
        this.useCaseName = useCaseName;
        this.args = args;
        if (onSuccessCallback != null) {
            this.onSuccessCallback = new WeakReference<>(onSuccessCallback);
        }

        if (onErrorCallback != null) {
            this.onErrorCallback = new WeakReference<>(onErrorCallback);
        }
    }

    public OnSuccessCallback getOnSuccessCallback() {
        return onSuccessCallback != null ? onSuccessCallback.get() : null;
    }

    public OnErrorCallback getOnErrorCallback() {
        return onErrorCallback != null ? onErrorCallback.get() : null;
    }

    String getUseCaseName() {
        return useCaseName;
    }

    Object[] getArgs() {
        return args;
    }
}
