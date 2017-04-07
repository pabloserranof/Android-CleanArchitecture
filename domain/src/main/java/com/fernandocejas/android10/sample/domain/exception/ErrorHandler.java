package com.fernandocejas.android10.sample.domain.exception;

import com.fernandocejas.android10.sample.domain.interactor.CallbackScheduler;
import com.fernandocejas.android10.sample.domain.interactor.MainThreadCallbackScheduler;
import com.fernandocejas.android10.sample.domain.interactor.OnErrorCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class ErrorHandler {

    private final List<OnErrorCallback> errorCallbacks = new ArrayList<>();
    private ErrorFactory errorFactory;
    private CallbackScheduler callbackScheduler;

    @Inject
    public ErrorHandler() {
        this(new ErrorFactory(), new MainThreadCallbackScheduler());
    }

    public ErrorHandler(ErrorFactory errorFactory) {
        this(errorFactory, new MainThreadCallbackScheduler());
    }

    public ErrorHandler(CallbackScheduler callbackScheduler) {
        this(new ErrorFactory(), callbackScheduler);
    }

    public ErrorHandler(ErrorFactory errorFactory, CallbackScheduler callbackScheduler) {
        this.errorFactory = errorFactory;
        this.callbackScheduler = callbackScheduler;
    }

    public void setErrorFactory(ErrorFactory errorFactory) {
        if (errorFactory == null) {
            throw new IllegalArgumentException("errorFactory can not be null");
        }
        this.errorFactory = errorFactory;
    }

    public void setCallbackScheduler(CallbackScheduler callbackScheduler) {
        if (callbackScheduler == null) {
            throw new IllegalArgumentException("callbackScheduler can not be null");
        }
        this.callbackScheduler = callbackScheduler;
    }

    public void notifyException(Exception exception, OnErrorCallback useCaseErrorCallback) {
        final Error error = createError(exception);
        notifyError(error, useCaseErrorCallback);
    }

    public void notifyError(Error error, OnErrorCallback useCaseErrorCallback) {
        List<OnErrorCallback> callbacks = new ArrayList<>();
        if (useCaseErrorCallback != null) {
            callbacks.add(useCaseErrorCallback);
        }
        callbacks.addAll(errorCallbacks);
        notifyErrorOnScheduler(error, callbacks);
    }

    public void registerCallback(OnErrorCallback onErrorCallback) {
        errorCallbacks.add(onErrorCallback);
    }

    public void unregisterCallback(OnErrorCallback onErrorCallback) {
        errorCallbacks.remove(onErrorCallback);
    }

    private Error createError(Exception exception) {
        Error error = null;
        if (errorFactory != null) {
            error = errorFactory.create(exception);

            if (error == null) {
                error = errorFactory.createInternalException(exception);
            }
        }

        if (error == null) {
            error = new Error("Generic Error", exception);
        }

        return error;
    }

    private void notifyErrorOnScheduler(final Error error,
                                        final Collection<OnErrorCallback> callbacks) {
        callbackScheduler.post(new Runnable() {
            @Override public void run() {
                boolean isConsumed;
                for (OnErrorCallback errorCallback : callbacks) {
                    isConsumed = errorCallback.onError(error);
                    if (isConsumed) {
                        break;
                    }
                }
            }
        });
    }
}

