package com.fernandocejas.android10.sample.domain;

/**
 * Implement this interface to provide a system that can execute Use Cases. You need to implement
 * the method execute and put the use case out of the main thread.
 */
public interface TaskScheduler<T> {
    void execute(T useCaseWrapperJob);
}