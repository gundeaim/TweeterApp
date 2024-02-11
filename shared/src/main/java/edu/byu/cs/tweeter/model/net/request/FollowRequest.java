package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
//Response will just be the class Response as it doesn't have anything to pass back
public class FollowRequest {
    private AuthToken authToken;
    private String selectedUser;

    private FollowRequest() {}

    public FollowRequest(AuthToken authToken, String selectedUser) {
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
