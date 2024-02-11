package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.FollowerResponse;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

public class StatusService {
    public PostStatusResponse postStatus(PostStatusRequest request) {
        if(request.getStatusMessage() == null){
            throw new RuntimeException("[Bad Request] Missing a status");
        }
        String message = "Success";
        return new PostStatusResponse(true, message);
    }

    public GetStoryResponse getStory(GetStoryRequest request) {
        if(request.getFollowerAlias() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }
        Pair<List<Status>, Boolean> pageOfItems = getDummyItems(request.getLastStatus(), request.getLimit());
        List<Status> items = pageOfItems.getFirst();
        boolean hasMorePages = pageOfItems.getSecond();

        String message = "Success";
        return new GetStoryResponse(items, hasMorePages);

    }

    public GetFeedResponse getFeed(GetFeedRequest request) {
        if(request.getFollowerAlias() == null){
            throw new RuntimeException("[Bad Request] Request needs to have a follower alias");
        } else if(request.getLimit() <= 0) {
            throw new RuntimeException("[Bad Request] Request needs to have a positive limit");
        }
        Pair<List<Status>, Boolean> pageOfItems = getDummyItems(request.getLastStatus(), request.getLimit());
        List<Status> items = pageOfItems.getFirst();
        boolean hasMorePages = pageOfItems.getSecond();

        String message = "Success";
        return new GetFeedResponse(items, hasMorePages);
    }

    public Pair<List<Status>, Boolean> getDummyItems(Status  lastItem, int limit){
        return getFakeData().getPageOfStatus(lastItem, limit);
    }

    FakeData getFakeData() {
        return FakeData.getInstance();
    }


}
