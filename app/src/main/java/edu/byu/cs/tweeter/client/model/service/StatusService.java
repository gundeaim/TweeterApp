package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PagedNotificationHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PagedNotificationObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends Service {

    public static final String POST_STATUS_URL_PATH = "/poststatus";

    public void loadMoreStoryItems(User user, int pageSize, Status lastStatus, PagedNotificationObserver observer) {
        GetStoryTask getStoryTask = new GetStoryTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new PagedNotificationHandler(observer));
        executeTask(getStoryTask);
    }

    public void loadMoreItems(User user, int pageSize, Status lastItem, PagedNotificationObserver observer) {
        GetFeedTask getFeedTask = new GetFeedTask(Cache.getInstance().getCurrUserAuthToken(), user, pageSize, lastItem, new PagedNotificationHandler(observer));
        executeTask(getFeedTask);
    }

    public void postStatus(Status newStatus, SimpleNotificationObserver observer) {
        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
                newStatus, new SimpleNotificationHandler(observer));
        executeTask(statusTask);
    }
}
