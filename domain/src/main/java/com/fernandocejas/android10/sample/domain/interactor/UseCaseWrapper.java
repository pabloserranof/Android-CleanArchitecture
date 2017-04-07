package com.fernandocejas.android10.sample.domain.interactor;

public final class UseCaseWrapper<Params> {
    private final UseCase useCase;
    private final Params useCaseParams;
    private final DefaultObserver defaultObserver;

    public UseCaseWrapper(UseCase useCase, Params useCaseParams, DefaultObserver defaultObserver) {
        this.useCase = useCase;
        this.useCaseParams = useCaseParams;
        this.defaultObserver = defaultObserver;
    }

    public void execute() {
        useCase.execute(defaultObserver, useCaseParams);
    }
}
