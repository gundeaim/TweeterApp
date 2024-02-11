package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.FollowCountObserver;

public class FollowCountHandler extends BackgroundTaskHandler<FollowCountObserver> {

    public FollowCountHandler(FollowCountObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, FollowCountObserver observer) {
        int count = data.getInt(GetFollowersCountTask.COUNT_KEY);
        observer.handleSuccess(count);
    }
}
