package edu.byu.cs.tweeter.client.model.service.backgroundTask.observer;

public interface ServiceObserver {
    void displayError(String message);
    void displayException(Exception exception);
}
