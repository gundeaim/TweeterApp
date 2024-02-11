package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.AuthenticateTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.CurrentUserObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticateHandler extends BackgroundTaskHandler<CurrentUserObserver> {
    public AuthenticateHandler(CurrentUserObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, CurrentUserObserver observer) {
        User currUser = (User) data.getSerializable(AuthenticateTask.USER_KEY);
        AuthToken authToken = (AuthToken) data.getSerializable(AuthenticateTask.AUTH_TOKEN_KEY);

        Cache.getInstance().setCurrUser(currUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);

        observer.handleSuccess(currUser);
    }
}
