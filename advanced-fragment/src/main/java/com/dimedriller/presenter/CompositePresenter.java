package com.dimedriller.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

public class CompositePresenter<V extends ContainerViewInterface, M> extends Presenter<V, M>
        implements PresenterContainer {
    private static final String PARAM_PRESENTER_MANAGER_INSTANCE_STATE = CompositePresenter.class.getName()
            + ".PRESENTER_MANAGER_INSTANCE_STATE";

    private final @NonNull PresenterContainer mContainer;
    private final @NonNull PresenterManager mPresenterManager;

    public CompositePresenter(@NonNull Class<V> viewInterfaceClass,
            @NonNull PresenterContainer container,
            @NonNull Bundle params) {
        super(viewInterfaceClass, container, params);

        mContainer = container;
        mPresenterManager = new PresenterManager(this);

        PresenterManagerInstanceState managerInstanceState
                = params.getParcelable(PARAM_PRESENTER_MANAGER_INSTANCE_STATE);
        mPresenterManager.restoreInstanceState(managerInstanceState);
    }

    @Override
    public void onSaveState(@NonNull Bundle state) {
        super.onSaveState(state);

        PresenterManagerInstanceState managerInstanceState = mPresenterManager.saveInstanceState();
        state.putParcelable(PARAM_PRESENTER_MANAGER_INSTANCE_STATE, managerInstanceState);
    }

    @NonNull
    @Override
    public PresenterActivity getActivity() {
        return mContainer.getActivity();
    }

    @NonNull
    @Override
    public ViewGroup getContainerView() {
        return mViewInterface.getContainerView();
    }

    @NonNull
    @Override
    public PresenterManager getPresenterManager() {
        return mPresenterManager;
    }
}
