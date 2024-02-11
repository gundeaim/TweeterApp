package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.CheckFollowerObserver;

public class CheckFollowerHandler extends BackgroundTaskHandler<CheckFollowerObserver> {

    public CheckFollowerHandler(CheckFollowerObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, CheckFollowerObserver observer) {
        boolean isFollower = data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
        observer.handleSuccess(isFollower);
    }
}
