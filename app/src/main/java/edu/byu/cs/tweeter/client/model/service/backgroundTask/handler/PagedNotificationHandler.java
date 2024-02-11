package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PagedNotificationObserver;

public class PagedNotificationHandler<T> extends BackgroundTaskHandler<PagedNotificationObserver> {
    public PagedNotificationHandler(PagedNotificationObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccess(Bundle data, PagedNotificationObserver observer) {
        List<T> items = (List<T>) data.getSerializable(GetFollowersTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(GetFollowersTask.MORE_PAGES_KEY);
        observer.handleSuccess(items, hasMorePages);
    }
}
