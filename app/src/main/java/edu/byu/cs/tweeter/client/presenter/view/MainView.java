package edu.byu.cs.tweeter.client.presenter.view;

public interface MainView extends PresenterView {

    void enableButton();
    void disableButton();

    void disableFollowButton();

    void enableFollowButton();

    void setFollowButton(boolean value);

    void SetFollowerCount(int count);
    void SetFolloweeCount(int count);

    void logoutSuccess();

    void displaySuccess();

    void setLogTag(Exception ex);

}
