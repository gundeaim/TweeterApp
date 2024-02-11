package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.CurrentUserObserver;
import edu.byu.cs.tweeter.model.domain.User;
public class GetCurrentUserHandler extends BackgroundTaskHandler<CurrentUserObserver> {

    public GetCurrentUserHandler(CurrentUserObserver observer) {
        super(observer);
    }


    @Override
    protected void handleSuccess(Bundle data, CurrentUserObserver observer) {
        User user = (User) data.getSerializable(GetUserTask.USER_KEY);
        observer.handleSuccess(user);
    }
}
