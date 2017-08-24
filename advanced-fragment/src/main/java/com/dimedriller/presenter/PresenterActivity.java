package com.dimedriller.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

public abstract class PresenterActivity<VI extends ContainerViewInterface>
        extends AppCompatActivity
        implements PresenterContainer {
    private static final String PARAM_PRESENTER_MANAGER_INSTANCE_STATE = PresenterActivity.class.getName()
            + ".PRESENTER_MANAGER_INSTANCE_STATE";

    private final VI mViewInterface;
    private final PresenterManager mPresenterManager = new PresenterManager(this);
    private final SimpleViewPlacer mViewPlacer = new SimpleViewPlacer();

    public PresenterActivity(Class<VI> viClass) {
        mViewInterface = ViewInterface.createViewInterface(viClass);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
        mViewInterface.createView(rootView, mViewPlacer);

        if (savedInstanceState != null) {
            PresenterManagerInstanceState managerInstanceState = savedInstanceState.getParcelable(
                    PARAM_PRESENTER_MANAGER_INSTANCE_STATE);
            mPresenterManager.restoreInstanceState(managerInstanceState);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenterManager.destroy();

        ViewGroup rootView = (ViewGroup) findViewById(android.R.id.content);
        mViewInterface.destroyView(rootView, mViewPlacer);

        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        PresenterManagerInstanceState managerInstanceState = mPresenterManager.saveInstanceState();
        outState.putParcelable(PARAM_PRESENTER_MANAGER_INSTANCE_STATE, managerInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenterManager.resume();
    }

    @Override
    protected void onPause() {
        mPresenterManager.pause();

        super.onPause();
    }

    @Override
    public @NonNull PresenterActivity getActivity() {
        return this;
    }

    @Override
    public @NonNull ViewGroup getContainerView() {
        return mViewInterface.getContainerView();
    }

    @Override
    public @NonNull PresenterManager getPresenterManager() {
        return mPresenterManager;
    }
}
