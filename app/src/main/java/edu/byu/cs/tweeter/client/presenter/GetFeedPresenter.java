package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.presenter.view.PagedView;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFeedPresenter extends PagedPresenter<Status>{
    public GetFeedPresenter(PagedView view) {
        super(view);
    }

    @Override
    protected void loadItemsPerService(User user, int pageSize, Status lastItem, PagedPresenter<Status>.GetPagedObserver observer) {
        getStatusService().loadMoreItems(user, pageSize, lastItem, observer);
    }

    @Override
    protected String getPagedPrefix() {
        return "Failed to get feed because of exception: ";
    }
}
