package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

public abstract class PresenterActivity<VI extends ContainerViewInterface>
        extends AppCompatActivity
        implements PresenterContainer {
    private final VI mViewInterface;
    private final PresenterManager mPresenterManager = new PresenterManager(this);

    public PresenterActivity(Class<VI> viClass) {
        mViewInterface = ViewInterface.createViewInterface(viClass);
    }

    @Override
    public @NonNull PresenterActivity getActivity() {
        return this;
    }

    @Override
    public @NonNull ViewGroup getAnchorView(@NonNull ViewAnchor anchor) {
        return anchor.findAnchorView(mViewInterface.getContainerView());
    }

    @Override
    public @NonNull PresenterManager getPresenterManager() {
        return mPresenterManager;
    }
}
