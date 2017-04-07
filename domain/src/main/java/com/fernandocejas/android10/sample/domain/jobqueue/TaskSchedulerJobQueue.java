package com.fernandocejas.android10.sample.domain.jobqueue;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.callback.JobManagerCallback;
import com.fernandocejas.android10.sample.domain.TaskScheduler;

import javax.inject.Inject;

/**
 * This is an implementation for a TaskScheduler based on the android-priority-job-queue.
 */

public class TaskSchedulerJobQueue implements TaskScheduler {

    private static final String TAG = TaskSchedulerJobQueue.class.getName();

    private final JobManager jobManager;

    @Inject
    public TaskSchedulerJobQueue(final JobManager jobManager) {
        this.jobManager = jobManager;
        this.jobManager.start();

        jobManager.addCallback(new JobManagerCallback() {
            @Override
            public void onJobAdded(@NonNull final Job job) {
                Log.d(TAG, "onJobAdded");
                // TODO Probably the only way to pass back a callback to notify the presenter
                // that the job was added and there is no network so show a retry notification
            }

            @Override
            public void onJobRun(@NonNull Job job, int resultCode) {
                Log.d(TAG, "onJobRun");
            }

            @Override
            public void onJobCancelled(@NonNull Job job, boolean byCancelRequest, @Nullable Throwable throwable) {
                Log.d(TAG, "onJobCancelled");
            }

            @Override
            public void onDone(@NonNull Job job) {
                Log.d(TAG, "onDone");
            }

            @Override
            public void onAfterJobRun(@NonNull Job job, int resultCode) {
                Log.d(TAG, "onAfterJobRun");
            }
        });
    }

    @Override
    public void execute(Job useCaseWrapperJob) {
        Log.d(TAG, "execute");
        jobManager.addJobInBackground(useCaseWrapperJob);
    }
}
