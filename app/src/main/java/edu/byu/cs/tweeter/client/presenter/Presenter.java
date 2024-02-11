package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.CurrentUserObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.ServiceObserver;
import edu.byu.cs.tweeter.client.presenter.view.PresenterView;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class Presenter<T extends PresenterView> {
    protected UserService userService;
    protected StatusService statusService;
    protected FollowService followService;

    public T getView() {
        return view;
    }

    private T view;

    public Presenter(T view) {
        this.view = view;
        userService = new UserService();
        followService = new FollowService();
    }

    protected StatusService getStatusService(){
        if(statusService == null){
            statusService = new StatusService();
        }
        return statusService;
    }

    protected abstract class Observer implements ServiceObserver{
        @Override
        public void displayError(String message) {
            String prefix = getErrorPrefix();
            view.displayMessage(prefix + message);
            adjustView();
        }

        @Override
        public void displayException(Exception exception) {
            String prefix = getPrefix();
            view.displayMessage(prefix + exception.getMessage());
            adjustView();
        }
        protected abstract void adjustView();

        protected abstract String getPrefix();
        protected abstract String getErrorPrefix();
    }


    protected class GetCurrentUserObserver extends Observer implements CurrentUserObserver {
        @Override
        public void handleSuccess(User currentUser) {
            view.displaySuccess(currentUser);
        }

        @Override
        protected void adjustView() {}

        @Override
        protected String getPrefix() {return getPresenterPrefix();}

        @Override
        protected String getErrorPrefix() {
            return "";
        }
    }

    protected abstract String getPresenterPrefix();
}
