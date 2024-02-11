package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.CheckFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.FollowCountHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagedNotificationHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.CheckFollowerObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PagedNotificationObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService extends Service{
    public static final String URL_PATH = "/getfollowing";
    public static final String FOLLOW_URL_PATH = "/follow";
    public static final String UNFOLLOW_URL_PATH = "/unfollow";
    public static final String GET_F_COUNT_URL_PATH = "/getfollowerscount";
    public static final String GET_FING_COUNT_URL_PATH = "/getfollowingcount";
    public static final String IS_FOLLOW_URL_PATH = "/isfollower";
    public static final String GET_FOLLOWER_URL_PATH = "/getfollowers";
    public static final String STORY_URL_PATH = "/getstory";
    public static final String FEED_URL_PATH = "/getfeed";


    public void updateUserFollowingAndFollowers(User selectedUser, edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.FollowCountObserver observer1, edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.FollowCountObserver observer2) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Get count of most recently selected user's followers.
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new FollowCountHandler(observer1));
        executor.execute(followersCountTask);

        // Get count of most recently selected user's followees (who they are following)
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new FollowCountHandler(observer2));
        executor.execute(followingCountTask);
    }

    public void loadMoreFollowers(User user, int pageSize, User lastFollower, PagedNotificationObserver observer){
        GetFollowersTask getFollowersTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollower, new PagedNotificationHandler(observer));
        executeTask(getFollowersTask);
    }

    public void loadMoreItems(User user, int pageSize, User lastItem, PagedNotificationObserver observer){
        GetFollowingTask getFollowingTask = new GetFollowingTask(Cache.getInstance().getCurrUserAuthToken(), user, pageSize, lastItem, new PagedNotificationHandler(observer));
        executeTask(getFollowingTask);
    }

    public void isFollower(User selectedUser, CheckFollowerObserver observer) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new CheckFollowerHandler(observer));
        executeTask(isFollowerTask);
    }

    public void unfollow(User selectedUser, SimpleNotificationObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new SimpleNotificationHandler(observer));
        executeTask(unfollowTask);
    }
    public void follow(User selectedUser, SimpleNotificationObserver observer) {
        FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new SimpleNotificationHandler(observer));
        executeTask(followTask);
    }
}
