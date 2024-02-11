package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetFollowersCountRequest {
    private AuthToken authToken;
    private String selectedUser;

    private GetFollowersCountRequest(){}

    public GetFollowersCountRequest(AuthToken authToken, String selectedUser) {
        this.authToken = authToken;
        this.selectedUser = selectedUser;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }
}
