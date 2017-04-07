package com.fernandocejas.android10.sample.domain.interactor;

import android.util.Log;

public final class UseCaseWrapper {
    private final UseCase useCase;
    private final UseCaseParams useCaseParams;
    private final DefaultObserver defaultObserver;

    public UseCaseWrapper(UseCase useCase, UseCaseParams useCaseParams, DefaultObserver defaultObserver) {
        this.useCase = useCase;
        this.useCaseParams = useCaseParams;
        this.defaultObserver = defaultObserver;
    }

    public void execute() {
        try {
            useCase.execute(defaultObserver, useCaseParams);
        }catch (Exception e){
            notifyException(e);
        }
    }

    private void notifyException(Exception exception) {
        if (defaultObserver != null) {
            Log.d("UseCaseWrapper", "notifyException");
            // TODO
           // errorHandler.notifyException(exception, useCaseParams.getOnErrorCallback());
        }
    }
}
