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

    private @NonNull ViewLocator mLocator = new ViewIDLocator(android.R.id.content);
    private @NonNull ViewPlacer mPlacer = new ViewSimplePlacer();

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

    final void setLocator(@NonNull ViewLocator locator) {
        mLocator = locator;
    }

    final void setPlacer(@NonNull ViewPlacer placer) {
        mPlacer = placer;
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

    final void createView(@NonNull ViewLocator locator, @NonNull ViewPlacer placer) {
        mLocator = locator;
        mPlacer = placer;

        ViewGroup anchorView = mContainer.getAnchorView(locator);
        anchorView.setSaveEnabled(false);

        mViewInterface.createView(anchorView, placer);
        onViewCreated();

        mViewInterface.restoreViewState();
    }

    final void createView() {
        createView(mLocator, mPlacer);
    }

    protected void onViewDestroyed() {
        // No action
    }

    final void destroyView(boolean isViewStateSaved) {
        if (isViewStateSaved)
            mViewInterface.saveViewState();

        ViewGroup anchorView = mContainer.getAnchorView(mLocator);
        mViewInterface.destroyView(anchorView, mPlacer);

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
