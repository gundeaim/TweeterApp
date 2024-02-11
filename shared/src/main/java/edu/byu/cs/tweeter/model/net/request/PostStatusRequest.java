package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class PostStatusRequest {
    private String statusMessage;
    private AuthToken authToken;

    private PostStatusRequest(){}

    public PostStatusRequest(AuthToken authToken, String statusMessage){
        this.authToken = authToken;
        this.statusMessage = statusMessage;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
