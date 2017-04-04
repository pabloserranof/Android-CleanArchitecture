package com.fernandocejas.android10.sample.domain.jobqueue;

import com.birbit.android.jobqueue.JobManager;
import com.fernandocejas.android10.sample.domain.TaskScheduler;
import com.fernandocejas.android10.sample.domain.interactor.UseCaseWrapper;

/**
 * This is an implementation for a TaskScheduler based on the android-priority-job-queue.
 */

public class TaskSchedulerJobQueue implements TaskScheduler {

    private JobManager jobManager;

    @Override
    public void execute(UseCaseWrapper useCaseWrapper) {

    }
}
