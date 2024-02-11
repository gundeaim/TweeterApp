package edu.byu.cs.tweeter.client.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.CheckFollowerObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.FollowCountObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.client.presenter.view.MainView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends Presenter<MainView> {
    public MainPresenter(MainView view){
        super(view);
    }

    @Override
    protected String getPresenterPrefix() {
        return "";
    }


    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    private User selectedUser;

    public void isFollower() {
        if (selectedUser.compareTo(Cache.getInstance().getCurrUser()) == 0) {
            getView().disableFollowButton();
        } else {
            getView().enableFollowButton();
            followService.isFollower(selectedUser, new IsFollowerObserver());
        }
    }

    public void toFollowOrNotToFollow(boolean value) {
        if (value) {
            followService.unfollow(selectedUser, new UnfollowObserver());
            getView().displayMessage("Removing " + selectedUser.getName() + "...");
        } else {
            followService.follow(selectedUser, new FollowObserver());
            getView().displayMessage("Adding " + selectedUser.getName() + "...");
        }
    }

    public void updateSelectedUserFollowingAndFollowers() {
        followService.updateUserFollowingAndFollowers(selectedUser, new FollowerCountObserver(), new FolloweeCountObserver());
    }

    public void postStatus(String post) {
        Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), System.currentTimeMillis(), parseURLs(post), parseMentions(post));
        getStatusService().postStatus(newStatus, new MainPresenter.PostingObserver());
    }

    public List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    public void logout() {
        userService.logout(new LogoutObserver());
    }

    private class IsFollowerObserver extends Observer implements CheckFollowerObserver {

        @Override
        public void handleSuccess(boolean isFollower){
            if (isFollower) {
                getView().enableButton();
            } else {
                getView().disableButton();
            }
        }
        @Override
        protected String getPrefix() {
            return "Failed to determine following relationship because of exception: ";
        }

        @Override
        protected String getErrorPrefix() {
            return "Failed to determine following relationship: ";
        }

        @Override
        protected void adjustView() {}

    }

    private class UnfollowObserver extends Observer implements SimpleNotificationObserver {
        @Override
        public void handleSuccess() {
            updateSelectedUserFollowingAndFollowers();
            getView().disableButton();
            getView().setFollowButton(true);
        }
        @Override
        protected void adjustView() {
            getView().setFollowButton(true);
        }

        @Override
        protected String getPrefix() {
            return "Failed to unfollow because of exception: ";
        }

        @Override
        protected String getErrorPrefix() {
            return "Failed to unfollow: ";
        }
    }

    private class FollowObserver extends Observer implements SimpleNotificationObserver {

        @Override
        public void handleSuccess() {
            updateSelectedUserFollowingAndFollowers();
            getView().enableButton();
            getView().setFollowButton(true);
        }

        @Override
        protected void adjustView() {
            getView().setFollowButton(true);
        }

        @Override
        protected String getPrefix() {
            return "Failed to follow because of exception: ";
        }

        @Override
        protected String getErrorPrefix() {
            return "Failed to follow: ";
        }
    }

    private class FollowerCountObserver extends Observer implements FollowCountObserver {
        @Override
        public void handleSuccess(int count) {
            getView().SetFollowerCount(count);
        }

        @Override
        protected void adjustView() {}

        @Override
        protected String getPrefix() {
            return "Failed to get followers count because of exception: " ;
        }

        @Override
        protected String getErrorPrefix() {
            return "Failed to get followers count: ";
        }


    }

    private class FolloweeCountObserver extends Observer implements FollowCountObserver{
        @Override
        public void handleSuccess(int count) {
            getView().SetFolloweeCount(count);
        }
        @Override
        protected void adjustView() {}

        @Override
        protected String getPrefix() {
            return "Failed to get following count because of exception: ";
        }

        @Override
        protected String getErrorPrefix() {
            return "Failed to get following: ";
        }


    }

    private class LogoutObserver extends Observer implements SimpleNotificationObserver{
        @Override
        protected void adjustView() {}

        @Override
        protected String getPrefix() {
            return "Failed to logout because of exception: ";
        }

        @Override
        protected String getErrorPrefix() {
            return "Failed to logout: ";
        }

        @Override
        public void handleSuccess() {
            Cache.getInstance().clearCache();
            getView().logoutSuccess();
        }
    }

    protected class PostingObserver extends Observer implements SimpleNotificationObserver{
        @Override
        protected void adjustView() {}

        @Override
        protected String getPrefix() {
            return "Failed to post status because of exception: ";
        }

        @Override
        protected String getErrorPrefix() {
            return "Failed to post status: ";
        }

        @Override
        public void handleSuccess() {
            getView().displaySuccess();
        }
    }
}
