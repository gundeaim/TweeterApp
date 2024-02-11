package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetFollowersCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowersCountResponse;

public abstract class GetCountTask extends AuthenticatedTask {
    private static final String LOG_TAG = "GetCountTask";

    public static final String COUNT_KEY = "count";
    private ServerFacade serverFacade;

    /**
     * The user whose count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    protected final User targetUser;

    protected int count;

    protected GetCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(authToken, messageHandler);
        this.targetUser = targetUser;
    }

    protected User getTargetUser() {
        return targetUser;
    }

    @Override
    protected void runTask() {

        runCountTask();
    }

    protected abstract void runCountTask();

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putInt(COUNT_KEY, count);
    }
}
