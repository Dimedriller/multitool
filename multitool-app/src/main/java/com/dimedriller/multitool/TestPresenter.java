package com.dimedriller.multitool;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dimedriller.multitoolmodel.MultitoolModel;
import com.dimedriller.presenter.Presenter;
import com.dimedriller.presenter.PresenterContainer;

public class TestPresenter extends Presenter<TestPresenterViewInterface, MultitoolModel> {
    public TestPresenter(@NonNull PresenterContainer container, @NonNull Bundle params) {
        super(TestPresenterViewInterface.class, container, params);
    }
}
