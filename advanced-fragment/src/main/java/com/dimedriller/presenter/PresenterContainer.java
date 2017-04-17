package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

public interface PresenterContainer {
    @NonNull PresenterActivity getActivity();
    @NonNull ViewGroup getAnchorView(@NonNull ViewLocator anchor);
    @NonNull PresenterManager getPresenterManager();
}
