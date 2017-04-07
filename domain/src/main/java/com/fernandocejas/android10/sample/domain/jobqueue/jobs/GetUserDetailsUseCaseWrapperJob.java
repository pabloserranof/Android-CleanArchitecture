package com.fernandocejas.android10.sample.domain.jobqueue.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.fernandocejas.android10.sample.domain.interactor.UseCaseWrapper;

/**
 * Job extension created to be able to execute a use case using android-priority-job-queue.
 * <p>
 * Each job will have different requirements (persisted, network required and so on)
 * So we need a different wrapper for each UseCase
 * <p>
 * Requirements for the job GetUserListUseCaseWrapperJob:
 * 1. Requires Network
 * 2. Non persisted
 * 3. One single job in the queue
 * 4. Cancelled when the user press back TODO
 */

public class GetUserDetailsUseCaseWrapperJob extends Job {

    private static final int PRIORITY_NORMAL = 3;
    private final UseCaseWrapper useCaseWrapper;

    public GetUserDetailsUseCaseWrapperJob(UseCaseWrapper useCaseWrapper, int userId) {
        super(new Params(PRIORITY_NORMAL).requireNetwork().singleInstanceBy(String.valueOf(userId)));
        this.useCaseWrapper = useCaseWrapper;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        useCaseWrapper.execute();
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return RetryConstraint.createExponentialBackoff(runCount, 1000);
    }
}
