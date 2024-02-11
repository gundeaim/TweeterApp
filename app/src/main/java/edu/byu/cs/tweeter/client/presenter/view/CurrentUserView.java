package edu.byu.cs.tweeter.client.presenter.view;

import edu.byu.cs.tweeter.model.domain.User;

public interface CurrentUserView extends PresenterView{
    void handleSuccess(User user);
}
