package edu.byu.cs.tweeter.client.model.service.backgroundTask.observer;

public interface CheckFollowerObserver extends ServiceObserver {
    void handleSuccess(boolean isFollower);
}
