package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.presenter.view.LoginView;

public class LoginPresenter extends Presenter<LoginView>{

    public LoginPresenter(LoginView view) {
        super(view);
    }



    public void initiateLogin(String username, String password){
        String validationMessage = validateLogin(username, password);

        if (validationMessage == null) {
            userService.login(username, password, new GetCurrentUserObserver());
        } else{
            getView().displayErrorMessage(validationMessage);
        }
    }

    public String validateLogin(String username, String password) {
        if (username.length() > 0 && username.charAt(0) != '@') {
           return"Alias must begin with @.";
        }
        if (username.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty." ;
        }
        return null;
    }

    @Override
    protected String getPresenterPrefix() {
        return "";
    }
}
