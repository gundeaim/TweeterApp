package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.presenter.view.PagedView;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFollowersPresenter extends PagedPresenter<User> {

    public GetFollowersPresenter(PagedView view){
        super(view);
    }
    @Override
    protected void loadItemsPerService(User user, int pageSize, User lastItem, PagedPresenter<User>.GetPagedObserver observer) {
        followService.loadMoreFollowers(user, pageSize, lastItem, observer);
    }
    @Override
    protected String getPagedPrefix() {
        return "Failed to get followers because of exception: ";
    }

}
