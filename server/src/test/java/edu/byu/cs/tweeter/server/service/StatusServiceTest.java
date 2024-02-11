package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.server.dao.FollowDAO;

public class StatusServiceTest {

    private GetStoryRequest request;
    private GetStoryResponse expectedResponse;
    private StatusService mockService;
    private StatusService statusServiceSpy;

    @BeforeEach
    public void setup() {
        AuthToken authToken = new AuthToken();

        User currentUser = new User("FirstName", "LastName", null);
        List<String> urls = Collections.singletonList("https://byu.edu");

        Status resultStatus1 = new Status("post", currentUser, System.currentTimeMillis(), urls, Collections.singletonList("@frank"));
        Status resultStatus2 = new Status("post2", currentUser, System.currentTimeMillis(), urls, Collections.singletonList("@frank2"));
        Status resultStatus3 = new Status("post3", currentUser, System.currentTimeMillis(), urls, Collections.singletonList("@frank3"));

        // Setup a request object to use in the tests
        request = new GetStoryRequest(authToken, currentUser.getAlias(), 3, null);

        // Setup a mock FollowDAO that will return known responses
        expectedResponse = new GetStoryResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);

        statusServiceSpy = Mockito.spy(StatusService.class);
        Mockito.when(statusServiceSpy.getStory(request)).thenReturn(expectedResponse);
    }

    /**
     * Verify that the {@link FollowService#getFollowing(FollowingRequest)}
     * method returns the same result as the {@link FollowDAO} class.
     */
    @Test
    public void testGetStory_validRequest_correctResponse() {
        GetStoryResponse response = statusServiceSpy.getStory(request);
        Assertions.assertEquals(expectedResponse, response);
    }
}
