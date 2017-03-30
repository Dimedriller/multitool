package com.dimedriller.presenter;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.dimedriller.advancedmodel.Model;

public abstract class Presenter<V extends ViewInterface, M> {
    private static final String STATE_NAME_PARAMS = "Params";

    protected final V mViewInterface;
    protected final M mModel;

    private final @NonNull PresenterContainer mContainer;
    private final @NonNull Bundle mParams;

    public Presenter(@NonNull Class<V> viewInterfaceClass,
            @NonNull PresenterContainer container,
            @NonNull Bundle params) {
        mViewInterface = ViewInterface.createViewInterface(viewInterfaceClass);

        PresenterActivity activity = container.getActivity();
        //noinspection unchecked
        Model.Container<M> modelContaner = (Model.Container<M>) activity.getApplicationContext();
        mModel = modelContaner.getModel();

        mContainer = container;
        mParams = params;
    }

    @CallSuper
    public void finish() {
        // No action
    }

    public @NonNull String getTag() {
        return getClass().getName();
    }

    @CallSuper
    public void onSaveState(@NonNull Bundle state) {
        state.putBundle(STATE_NAME_PARAMS, mParams);
    }

    @CallSuper
    public void onResume() {
        // No action
    }

    @CallSuper
    public void onPause() {
        // No action
    }

    protected void onViewCreated() {
        // No action
    }

    final void createView(ViewAnchor anchor) {
        ViewGroup anchorView = mContainer.getAnchorView(anchor);
        mViewInterface.createView(anchorView);
        onViewCreated();
    }
}
