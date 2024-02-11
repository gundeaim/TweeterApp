package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.AuthenticateHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.GetCurrentUserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.CurrentUserObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTaskUtils;


public class UserService extends Service {
    public static final String URL_PATH = "/login";
    public static final String LOGOUT_URL_PATH = "/logout";
    public static final String REGISTER_URL_PATH = "/register";
    public static final String USER_URL_PATH = "/getuser";

    /**
     * Creates an instance.
     *
     */
    public UserService() {
    }

    /**
     * Makes an asynchronous login request.
     *
     * @param username the user's name.
     * @param password the user's password.
     */
    public void login(String username, String password, CurrentUserObserver observer) {
        LoginTask loginTask = getLoginTask(username, password, observer);
        BackgroundTaskUtils.runTask(loginTask);
    }

    /**
     * Returns an instance of {@link LoginTask}. Allows mocking of the LoginTask class for
     * testing purposes. All usages of LoginTask should get their instance from this method to
     * allow for proper mocking.
     *
     * @return the instance.
     */
    LoginTask getLoginTask(String username, String password, CurrentUserObserver observer) {
        return new LoginTask(this, username, password, new AuthenticateHandler(observer));
    }

    public void logout(SimpleNotificationObserver observer) {
        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new SimpleNotificationHandler(observer));
        executeTask(logoutTask);
    }

    public void registerNewUser(String firstName, String lastName, String alias, String password, String imageBytesBase64, CurrentUserObserver registerObserver) {
        RegisterTask registerTask = new RegisterTask(firstName, lastName,
                alias, password, imageBytesBase64, new AuthenticateHandler(registerObserver));
        executeTask(registerTask);
    }

    public void getUser(String username, CurrentUserObserver getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                username, new GetCurrentUserHandler(getUserObserver));
        executeTask(getUserTask);
    }
}
