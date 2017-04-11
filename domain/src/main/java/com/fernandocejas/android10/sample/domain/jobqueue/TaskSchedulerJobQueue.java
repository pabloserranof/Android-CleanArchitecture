package com.fernandocejas.android10.sample.domain.jobqueue;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.callback.JobManagerCallback;
import com.fernandocejas.android10.sample.domain.TaskScheduler;

import javax.inject.Inject;

/**
 * This is an implementation for a TaskScheduler based on the android-priority-job-queue.
 */

public class TaskSchedulerJobQueue implements TaskScheduler<Job> {

    private final JobManager jobManager;

    @Inject
    public TaskSchedulerJobQueue(final JobManager jobManager) {
        this.jobManager = jobManager;
        this.jobManager.start();

        jobManager.addCallback(new JobManagerCallback() {
            @Override
            public void onJobAdded(@NonNull final Job job) {
                // TODO Probably the only way to pass back a callback to notify the UI
                // that the job was added and there is no network so show a retry notification
            }

            @Override
            public void onJobRun(@NonNull Job job, int resultCode) {
            }

            @Override
            public void onJobCancelled(@NonNull Job job, boolean byCancelRequest, @Nullable Throwable throwable) {
            }

            @Override
            public void onDone(@NonNull Job job) {
            }

            @Override
            public void onAfterJobRun(@NonNull Job job, int resultCode) {
            }
        });
    }

    @Override
    public void execute(Job useCaseWrapperJob) {
        jobManager.addJobInBackground(useCaseWrapperJob);
    }
}
