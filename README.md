Android-CleanArchitecture with JobScheduler [![Build Status](https://travis-ci.org/android10/Android-CleanArchitecture.svg?branch=master)](https://travis-ci.org/android10/Android-CleanArchitecture)
=========================

This is a fork of the Android MVP Clean Architecture template project https://github.com/android10/Android-CleanArchitecture

The problem in this architecture
--------------------------------
There is no infrastructure to automatically retry unsuccessful Use Cases. If a Use Case fails for some reason (typically due to a network error but not neccesarily) the UI shows an error message and the user can manually retry the Use Case.

A solution: Clean Archiceture with JobScheduler
-----------------------------------------------
This template project adds a Job Scheduler to the original one to allow to automatically retry and customize the conditions of unsucessful Use Cases.

The Job Scheduler
-----------------
This template project uses Android-priority-jobqueue as the Job Scheduler https://github.com/yigit/android-priority-jobqueue
Other alternatives are GCM Network Manager (requires Google Play Services) or Android-job by Evernote.

The changes in Clean Architecture
----------------------------------
1. Wrap your Use Cases into UseCaseWrapper(UseCase, Params, ObserverCallbacks). Your Use Cases shouldn't be modified or affected at all.
2. Wrap and extend each UseCaseWrapper into Jobs (concrete implementation of the JobScheduler). Indicate the requirements of this Job. Example: requireNetwork, persist, unique, retry policy, etc.
3. Instead of executing the Use Cases in the Presenters inject the Job Scheduler in the presenters and add the UseCaseWrapperJob to the Job Scheduler. The Job Scheduler will take care of them according to their requirements.
     
     `jobScheduler.execute(useCaseWrapperJob)`



