package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.net.TweeterRequestException;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowerRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask extends AuthenticatedTask {
    private static final String LOG_TAG = "FollowTask";
    /**
     * The user that is being followed.
     */
    private final User followee;
    private ServerFacade serverFacade;

    public FollowTask(AuthToken authToken, User followee, Handler messageHandler) {
        super(authToken, messageHandler);
        this.followee = followee;
    }

    @Override
    protected void runTask() {
        try {
            String followeeAlias = followee == null ? null : followee.getAlias();

            FollowRequest request = new FollowRequest(authToken, followeeAlias);
            FollowResponse response = getServerFacade().follow(request, FollowService.FOLLOW_URL_PATH);

            if(response.isSuccess()){
                sendSuccessMessage();
            } else{
                sendFailedMessage(response.getMessage());
            }
        } catch (IOException | TweeterRemoteException ex){
            Log.e(LOG_TAG, "Failed to follow", ex);
            sendExceptionMessage(ex);
        }
    }



    ServerFacade getServerFacade() {
        if(serverFacade == null) {
            serverFacade = new ServerFacade();
        }

        return serverFacade;
    }

}
