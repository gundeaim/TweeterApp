package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.presenter.view.PagedView;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowingPresenter extends PagedPresenter<User>{
    public GetFollowingPresenter(PagedView view) {
        super(view);
    }
    @Override
    protected void loadItemsPerService(User user, int pageSize, User lastItem, GetPagedObserver observer) {
        followService.loadMoreItems(user, pageSize, lastItem, observer);
    }
    @Override
    protected String getPagedPrefix()  {
        return "Failed to get following because of exception: ";
    }
}
