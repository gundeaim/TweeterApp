package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetFollowersCountRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowingCountRequest;
import edu.byu.cs.tweeter.model.net.response.GetFollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowingCountResponse;

/**
 * Background task that queries how many other users a specified user is following.
 */
public class GetFollowingCountTask extends GetCountTask {
    private static final String LOG_TAG = "GetFollowingCount";
    private ServerFacade serverFacade;
    public GetFollowingCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(authToken, targetUser, messageHandler);
    }

    @Override
    protected void runCountTask() {
        try {
            String targetUserAlias = targetUser == null ? null : targetUser.getAlias();

            GetFollowingCountRequest request = new GetFollowingCountRequest(authToken, targetUserAlias);
            GetFollowingCountResponse response = getServerFacade().getFollowingCount(request, FollowService.GET_FING_COUNT_URL_PATH);
            if (response.isSuccess()) {
                this.count = response.getCount();
                sendSuccessMessage();
            } else{
                sendFailedMessage(response.getMessage());
            }
        } catch (IOException | TweeterRemoteException ex){
            Log.e(LOG_TAG, "Failed to get followers count", ex);
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
