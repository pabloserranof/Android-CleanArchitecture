package com.fernandocejas.android10.sample.domain;

import com.fernandocejas.android10.sample.domain.interactor.UseCaseWrapper;

/**
 * Implement this interface to provide a system that can execute Use Cases. You need to implement
 * the method execute and put the use case out of the main thread.
 */
public interface TaskScheduler {
    void execute(UseCaseWrapper useCaseWrapper);
}