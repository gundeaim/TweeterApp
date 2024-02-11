package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.presenter.view.PresenterView;

public class RegisterPresenter extends Presenter<PresenterView>{

    public RegisterPresenter(PresenterView view) {
        super(view);
    }
    public void validateRegistration(int firstNameLength, int lastNameLength, int aliasLength, char aliasFirstChar, int passwordLength, boolean imageOk) {
        if (firstNameLength == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastNameLength == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (aliasLength == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }
        if (aliasFirstChar != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (aliasLength < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (passwordLength == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (imageOk) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }

    public void registerNewUser(String firstName, String lastName, String alias, String password, String image) {
        userService.registerNewUser(firstName, lastName, alias, password, image, new GetCurrentUserObserver());
    }

    @Override
    protected String getPresenterPrefix() {
        return "Failed to register because of exception: ";
    }
}
