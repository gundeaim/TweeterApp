package edu.byu.cs.tweeter.model.net.response;

public class UnfollowResponse extends Response{
    public UnfollowResponse(String message) {
        super(false, message);
    }

    public UnfollowResponse(boolean success, String message) {
        super(success, null);
    }
}
