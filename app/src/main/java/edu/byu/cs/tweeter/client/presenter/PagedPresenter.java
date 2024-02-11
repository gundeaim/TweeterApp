package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PagedNotificationObserver;
import edu.byu.cs.tweeter.client.presenter.view.PagedView;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends Presenter<PagedView>{

    public PagedPresenter(PagedView view) {
        super(view);
    }

    private static final int PAGE_SIZE = 10;
    private T lastItem;

    private boolean hasMorePages;
    private boolean isLoading = false;


    public boolean hasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }
    public boolean isLoading() {
        return isLoading;
    }
    public void getUser(String username) {
        userService.getUser(username, new GetCurrentUserObserver());
    }
    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            setLoading(true);
            loadItemsPerService(user, PAGE_SIZE, lastItem, new GetPagedObserver());
        }
    }

    private void setLoading(boolean value) {
        isLoading = value;
        getView().setLoadingFooter(value);
    }

    protected abstract void loadItemsPerService(User user, int pageSize, T lastItem, GetPagedObserver observer);

    protected class GetPagedObserver implements PagedNotificationObserver<T> {

        @Override
        public void handleSuccess(List<T> items, boolean hasMorePages) {
            setLoading(false);
            lastItem = (items.size() > 0) ? items.get(items.size() - 1) : null;
            setHasMorePages(hasMorePages);
            getView().addItems(items);
        }

        @Override
        public void displayError(String message) {
            setLoading(false);
            getView().displayMessage(message);
        }

        @Override
        public void displayException(Exception ex) {
            setLoading(false);
            String prefix = getPagedPrefix();
            getView().displayMessage(prefix + ex.getMessage());
        }
    }


    @Override
    protected String getPresenterPrefix() {
        return "Failed to get user's profile because of exception: ";
    }

    protected abstract String getPagedPrefix();

}
