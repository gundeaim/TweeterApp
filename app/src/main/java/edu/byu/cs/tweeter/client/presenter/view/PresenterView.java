package edu.byu.cs.tweeter.client.presenter.view;

import edu.byu.cs.tweeter.model.domain.User;

public interface PresenterView {
    void displayMessage(String message);
    void displaySuccess(User user);

}
