package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

public class UserService {

    public LoginResponse login(LoginRequest request) {
        if(request.getUsername() == null){
            throw new RuntimeException("[Bad Request] Missing a username");
        } else if(request.getPassword() == null) {
            throw new RuntimeException("[Bad Request] Missing a password");
        }

        // TODO: Generates dummy data. Replace with a real implementation.
        User user = getDummyUser();
        AuthToken authToken = getDummyAuthToken();
        return new LoginResponse(user, authToken);
    }

    public GetUserResponse getUser(GetUserRequest request) {
        if(request.getSelectedUser() == null){
            throw new RuntimeException("[Bad Request] Missing a selected user");
        }
        User user = getDummyUserByAlias(request.getSelectedUser());
        return new GetUserResponse(user);
    }

    private User getDummyUserByAlias(String alias) {
        return getFakeData().findUserByAlias(alias);
    }

    public RegisterResponse register(RegisterRequest request) {
        if(request.getUsername() == null){
            throw new RuntimeException("[Bad Request] Missing a username");
        } else if (request.getFirstName() == null) {
            throw new RuntimeException("[Bad Request] Missing a first name");
        } else if (request.getLastName() == null) {
            throw new RuntimeException("[Bad Request] Missing a last name");
        } else if (request.getPassword() == null){
            throw new RuntimeException("[Bad Request] Missing a password");
        } else if (request.getImage() == null){
            throw new RuntimeException("[Bad Request] Missing a image");
        }

        User user = getDummyUser();
        AuthToken authToken = getDummyAuthToken();

        String message = "Success";
        return new RegisterResponse(user, authToken);
    }

    /**
     * Returns the dummy user to be returned by the login operation.
     * This is written as a separate method to allow mocking of the dummy user.
     *
     * @return a dummy user.
     */
    User getDummyUser() {
        return getFakeData().getFirstUser();
    }


    /**
     * Returns the dummy auth token to be returned by the login operation.
     * This is written as a separate method to allow mocking of the dummy auth token.
     *
     * @return a dummy auth token.
     */
    AuthToken getDummyAuthToken() {
        return getFakeData().getAuthToken();
    }

    /**
     * Returns the {@link FakeData} object used to generate dummy users and auth tokens.
     * This is written as a separate method to allow mocking of the {@link FakeData}.
     *
     * @return a {@link FakeData} instance.
     */
    FakeData getFakeData() {
        return FakeData.getInstance();
    }

    public LogoutResponse logout(LogoutRequest request) {
        String message = "Success";
        return new LogoutResponse(true, message);
    }


}
