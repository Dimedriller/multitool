package com.dimedriller.presenter;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.dimedriller.advancedmodel.Model;

public abstract class Presenter<V extends ViewInterface, M> {
    protected final V mViewInterface;
    protected final M mModel;

    private final @NonNull PresenterContainer mContainer;
    private final @NonNull Bundle mParams;
    private final @NonNull String mTag;

    private @NonNull PresenterState mState = PresenterState.INIT;

    public Presenter(@NonNull Class<V> viewInterfaceClass,
            @NonNull PresenterContainer container,
            @NonNull Bundle params,
            @NonNull String tag) {
        mViewInterface = ViewInterface.createViewInterface(viewInterfaceClass);

        PresenterActivity activity = container.getActivity();
        //noinspection unchecked
        Model.Container<M> modelContaner = (Model.Container<M>) activity.getApplicationContext();
        mModel = modelContaner.getModel();

        mContainer = container;
        mParams = params;
        mTag = tag;
    }

    @CallSuper
    public void finish() {
        mState = PresenterState.DESTROYED;
    }

    public final @NonNull String getTag() {
        return mTag;
    }

    protected void onViewCreated() {
        // No action
    }

    final void createView(ViewAnchor anchor) {
        ViewGroup anchorView = mContainer.getAnchorView(anchor);
        mViewInterface.createView(anchorView);
        onViewCreated();
    }

    protected void onViewDestroyed() {
        // No action
    }

    final void destroyView(ViewAnchor anchor) {
        ViewGroup anchorView = mContainer.getAnchorView(anchor);
        mViewInterface.destroyView(anchorView);

        onViewDestroyed();
    }

    boolean hasView() {
        return mViewInterface.hasView();
    }

    @NonNull PresenterState getState() {
        return mState;
    }

    @CallSuper
    public void onResume() {
        // No action
    }

    void resume() {
        mState = PresenterState.RESUMED;
        onResume();
    }

    @CallSuper
    public void onPause() {
        // No action
    }

    void pause() {
        mState = PresenterState.PAUSED;
        onPause();
    }

    @CallSuper
    public void onSaveState(@NonNull Bundle state) {
        // TODO: Add persisting logic
    }


}
