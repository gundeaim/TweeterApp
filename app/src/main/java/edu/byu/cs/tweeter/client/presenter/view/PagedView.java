package edu.byu.cs.tweeter.client.presenter.view;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public interface PagedView<T> extends PresenterView {
    void setLoadingFooter(boolean value);

    void addItems(List<T> Items);
}
