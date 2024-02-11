package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.Random;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;

/**
 * Background task that determines if one user is following another.
 */
public class IsFollowerTask extends AuthenticatedTask {
    private static final String LOG_TAG = "IsFollowerTask";
    public static final String IS_FOLLOWER_KEY = "is-follower";

    private ServerFacade serverFacade;

    /**
     * The alleged follower.
     */
    private final User follower;

    /**
     * The alleged followee.
     */
    private final User followee;

    private boolean isFollower;

    public IsFollowerTask(AuthToken authToken, User follower, User followee, Handler messageHandler) {
        super(authToken, messageHandler);
        this.follower = follower;
        this.followee = followee;
    }

    @Override
    protected void runTask() {
        try {
            String followeeAlias = followee == null ? null : followee.getAlias();
            String followerAlias = follower == null ? null : follower.getAlias();

            IsFollowerRequest request = new IsFollowerRequest(authToken, followerAlias, followeeAlias);
            IsFollowerResponse response = getServerFacade().isFollower(request, FollowService.IS_FOLLOW_URL_PATH);

            if(response.isSuccess()){
                isFollower = response.getIsFollower();
                sendSuccessMessage();
            } else{
                sendFailedMessage(response.getMessage());
            }
        } catch (IOException | TweeterRemoteException ex){
            Log.e(LOG_TAG, "Failed to find isFollower", ex);
            sendExceptionMessage(ex);
        }
    }

    ServerFacade getServerFacade() {
        if(serverFacade == null) {
            serverFacade = new ServerFacade();
        }

        return serverFacade;
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putBoolean(IS_FOLLOWER_KEY, isFollower);
    }
}
