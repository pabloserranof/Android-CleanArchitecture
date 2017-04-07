package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.TaskScheduler;
import com.fernandocejas.android10.sample.domain.exception.ErrorHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Invoke methods annotated with UseCaseAnnotation annotation. The RosieUseCase instance will be executed out
 * of the Android main thread and the result of the operation will be provided asynchronously using
 * a callback.
 */
public class UseCaseHandler {

    private final TaskScheduler taskScheduler;
    private final ErrorHandler errorHandler;

    // This list is used to retain error adapters in a non-weak reference associated with this use
    // case handler
    private final List<OnErrorCallback> errorCallbacks = new ArrayList<>();

    @Inject
    public UseCaseHandler(TaskScheduler taskScheduler, ErrorHandler errorHandler) {
        this.taskScheduler = taskScheduler;
        this.errorHandler = errorHandler;
    }

    /**
     * Given a class configured with UseCaseAnnotation annotation executes the annotated
     * method out of the UI thread and returns the response, if needed, on the UI thread.
     *
     * @param useCase the use case to invoke.
     * @param useCaseParams params to use on the invocation.
     */
    void execute(UseCase useCase, UseCaseParams useCaseParams) {
//        UseCaseFilter.filter(useCase, useCaseParams);

      //  useCase.setOnSuccessCallback(useCaseParams.getOnSuccessCallback());

        OnErrorCallback onErrorCallback =
                new OnErrorCallbackToErrorHandlerAdapter(errorHandler, useCaseParams.getOnErrorCallback());
        errorCallbacks.add(onErrorCallback);
      //  useCase.setOnErrorCallback(onErrorCallback);

      //  UseCaseWrapper useCaseWrapper = new UseCaseWrapper(useCase, useCaseParams, errorHandler);
      //  taskScheduler.execute(useCaseWrapper);
    }

    public void registerGlobalErrorCallback(OnErrorCallback globalError) {
        errorHandler.registerCallback(globalError);
    }

    public void unregisterGlobalErrorCallback(OnErrorCallback globalError) {
        errorHandler.unregisterCallback(globalError);
    }

    /**
     * Inner class responsible for routing the errors thrown from the use case to the error handler
     */
    private static class OnErrorCallbackToErrorHandlerAdapter implements OnErrorCallback {
        private final WeakReference<ErrorHandler> errorHandler;
        private final WeakReference<OnErrorCallback> useCaseOnErrorCallback;

        public OnErrorCallbackToErrorHandlerAdapter(ErrorHandler errorHandler,
                                                    OnErrorCallback useCaseOnErrorCallback) {
            this.errorHandler = new WeakReference<>(errorHandler);
            this.useCaseOnErrorCallback = new WeakReference<>(useCaseOnErrorCallback);
        }

        @Override public boolean onError(Error error) {
            ErrorHandler errorHandler = this.errorHandler.get();
            if (errorHandler != null) {
                errorHandler.notifyError(error, useCaseOnErrorCallback.get());
                return true;
            }
            return false;
        }
    }
}
