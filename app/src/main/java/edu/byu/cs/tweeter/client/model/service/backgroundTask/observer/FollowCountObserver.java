package edu.byu.cs.tweeter.client.model.service.backgroundTask.observer;

public interface FollowCountObserver extends ServiceObserver{
    void handleSuccess(int count);
}
