package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class FollowingRequest {

    private AuthToken authToken;
    private String followerAlias;
    private int limit;
    private String lastFolloweeAlias;


    private FollowingRequest() {}

    public FollowingRequest(AuthToken authToken, String followerAlias, int limit, String lastFolloweeAlias) {
        this.authToken = authToken;
        this.followerAlias = followerAlias;
        this.limit = limit;
        this.lastFolloweeAlias = lastFolloweeAlias;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getFollowerAlias() {
        return followerAlias;
    }

    public void setFollowerAlias(String followerAlias) {
        this.followerAlias = followerAlias;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getLastFolloweeAlias() {
        return lastFolloweeAlias;
    }

    public void setLastFolloweeAlias(String lastFolloweeAlias) {
        this.lastFolloweeAlias = lastFolloweeAlias;
    }
}
