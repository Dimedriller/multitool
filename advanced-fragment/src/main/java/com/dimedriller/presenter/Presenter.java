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

    private @NonNull ViewPlacer mViewPlacer = new SimpleViewPlacer();

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

    final void setViewPlacer(@NonNull ViewPlacer viewPlacer) {
        mViewPlacer = viewPlacer;
    }

    final @NonNull String getTag() {
        return mTag;
    }

    @CallSuper
    protected void onCreate() {
        // No action
    }

    void create() {
        onCreate();
        mState = PresenterState.CREATED;
    }

    @CallSuper
    protected void onDestroy() {
        // No action
    }

    void destroy() {
        onDestroy();
        mState = PresenterState.DESTROYED;
    }

    protected void onViewCreated() {
        // No action
    }

    final void createView(@NonNull ViewPlacer viewPlacer) {
        mViewPlacer = viewPlacer;

        ViewGroup containerView = mContainer.getContainerView();
        mViewInterface.createView(containerView, viewPlacer);
        onViewCreated();

        mViewInterface.restoreViewState();
    }

    final void createView() {
        createView(mViewPlacer);
    }

    protected void onViewDestroyed() {
        // No action
    }

    final void destroyView(boolean isViewStateSaved) {
        if (isViewStateSaved)
            mViewInterface.saveViewState();

        ViewGroup containerView = mContainer.getContainerView();
        mViewInterface.destroyView(containerView, mViewPlacer);

        onViewDestroyed();
    }

    boolean hasView() {
        return mViewInterface.hasView();
    }

    @NonNull PresenterState getState() {
        return mState;
    }

    @CallSuper
    protected void onResume() {
        // No action
    }

    void resume() {
        mState = PresenterState.RESUMED;
        onResume();
    }

    @CallSuper
    protected void onPause() {
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

    Bundle saveState() {
        Bundle savedState = new Bundle();

        onSaveState(mParams);
        savedState.putBundle("params", mParams);

        return savedState;
    }
}
