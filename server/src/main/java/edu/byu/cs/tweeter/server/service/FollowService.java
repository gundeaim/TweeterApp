package edu.byu.cs.tweeter.server.service;

import java.util.List;
import java.util.Random;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowerRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowersCountRequest;
import edu.byu.cs.tweeter.model.net.request.GetFollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.FollowerResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.GetFollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link FollowDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowingResponse getFollowing(FollowingRequest request) {
        if(request.getFollowerAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }


        return getFollowingDAO().getFollowees(request);
    }

    public FollowerResponse getFollowers(FollowerRequest request) {
        if(request.getFolloweeAlias() == null) {
            throw new RuntimeException("[Bad Request] Request needs to have a followee alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }
        Pair<List<User>, Boolean> pageOfItems = getDummyItems(request.getLastFollowerAlias(), request.getLimit(), request.getFolloweeAlias());
        List<User> items = pageOfItems.getFirst();
        boolean hasMorePages = pageOfItems.getSecond();

        String message = "Success";
        return new FollowerResponse(items, hasMorePages);
    }

    public Pair<List<User>, Boolean> getDummyItems(String lastItem, int limit, String targetUserAlias){
        User lastFollower = getFakeData().findUserByAlias(lastItem);
        User targetUser = getFakeData().findUserByAlias(targetUserAlias);
        return getFakeData().getPageOfUsers(lastFollower, limit, targetUser);
    }

    FakeData getFakeData() {
        return FakeData.getInstance();
    }

    public FollowResponse follow(FollowRequest request){
        if(request.getSelectedUser() == null){
            throw new RuntimeException("[Bad Request] Missing a selected user");
        }
        String message = "Success";
        return new FollowResponse(true, message);
    }

    /**
     * Returns an instance of {@link FollowDAO}. Allows mocking of the FollowDAO class
     * for testing purposes. All usages of FollowDAO should get their FollowDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowDAO getFollowingDAO() {
        return new FollowDAO();
    }

    public UnfollowResponse unfollow(UnfollowRequest request) {
        if(request.getSelectedUser() == null){
            throw new RuntimeException("[Bad Request] Missing a selected user");
        }
        String message = "Success";
        return new UnfollowResponse(true, message);
    }

    public GetFollowersCountResponse GetFollowersCount(GetFollowersCountRequest request) {
        if(request.getSelectedUser() == null){
            throw new RuntimeException("[Bad Request] Missing a selected user");
        }
        int count = getDummyCount();
        return new GetFollowersCountResponse(count);
    }

    int getDummyCount(){
        return 20;
    }

    public GetFollowingCountResponse GetFollowingCount(GetFollowingCountRequest request) {
        if(request.getSelectedUser() == null){
            throw new RuntimeException("[Bad Request] Missing a selected user");
        }
        int count = getDummyCount();
        return new GetFollowingCountResponse(count);
    }

    boolean getDummyIsFollower(){
        return new Random().nextInt() > 0;
    }

    public IsFollowerResponse IsFollower(IsFollowerRequest request) {
        if(request.getFollowerAlias() == null){
            throw new RuntimeException("[Bad Request] Missing a follower");
        } else if (request.getFolloweeAlias() == null) {
            throw new RuntimeException("[Bad Request] Missing a followee");
        }
        boolean isFollower = getDummyIsFollower();
        return new IsFollowerResponse(isFollower);

    }


}
