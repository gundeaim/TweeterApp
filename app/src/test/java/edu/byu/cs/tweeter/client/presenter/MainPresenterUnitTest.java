package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.presenter.view.MainView;

public class MainPresenterUnitTest {
    private MainView mockView;
    private StatusService mockStatusService;
    private Cache mockCache;
    private MainPresenter mainPresenterSpy;

    @BeforeEach
    public void setup(){
        //Create mocks
        mockView = Mockito.mock(MainView.class);
        mockStatusService = Mockito.mock(StatusService.class);
        mockCache = Mockito.mock(Cache.class);

        mainPresenterSpy = Mockito.spy(new MainPresenter(mockView));
//        Mockito.doReturn(mockStatusService).when(mainPresenterSpy).getStatusService();
        Mockito.when(mainPresenterSpy.getStatusService()).thenReturn(mockStatusService);

        Cache.setInstance(mockCache);
    }

    @Test
    public void testPostStatus_postSuccessful(){
        Answer<Void> answer = new Answer<>(){
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainPresenter.PostingObserver observer = invocation.getArgument(1, MainPresenter.PostingObserver.class);
                observer.handleSuccess();
                return null;
            }
        };

        verifyCacheUse(answer);
        Mockito.verify(mockView).displaySuccess();
    }

    @Test
    public void testPostStatus_postFailedWithMessage(){
        Answer<Void> answer = new Answer<>(){
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainPresenter.PostingObserver observer = invocation.getArgument(1, MainPresenter.PostingObserver.class);
                observer.displayError("the error message");
                return null;
            }
        };

        verifyErrorResult("Failed to post status: the error message", answer);
    }

    @Test
    public void testPostStatues_postFailedWithException(){
        Answer<Void> answer = new Answer<>(){
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                MainPresenter.PostingObserver observer = invocation.getArgument(1, MainPresenter.PostingObserver.class);
                observer.displayException(new Exception("the exception message"));
                return null;
            }
        };

        verifyErrorResult("Failed to post status because of exception: the exception message", answer);
    }

    private void verifyErrorResult(String message, Answer<Void> answer) {
        verifyCacheUse(answer);
        Mockito.verify(mockView).displayMessage(message);
    }

    private void verifyCacheUse(Answer<Void> answer){
        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any());
        //mainPresenterSpy.postStatus("Test", long("MMM d yyyy h:mm aaa"));
        Mockito.verify(mockCache).getCurrUser();
    }
}
