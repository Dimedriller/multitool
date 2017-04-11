package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

public abstract class ContainerViewInterface extends ViewInterface {
    @Override
    protected abstract @NonNull ViewGroup onCreateView(@NonNull ViewGroup parentView);

    ViewGroup getContainerView() {
        return (ViewGroup) getRootView();
    }
}
