package com.davidlares.schedulerapp;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyService extends JobService {

    private static final String TAG = "SchedulerTag";

    public MyService() {
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.d(TAG, "OnStartJob: " + jobParameters.getJobId());
        // jobFinished(jobParameters, true);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // adding the local Broadcast
                Log.d(TAG, "Run: Job completed");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("ServiceMessage"));
                jobFinished(jobParameters, false);
            }
        };

        Thread thread = new Thread(r); // pass the runnable to the thread
        thread.start();

        // return false;
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "OnStopJob: " + jobParameters.getJobId());
        return false;
    }
}
