package com.dimedriller.presenter;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

public class ViewIDLocator implements ViewLocator {
    private final @IdRes int mID;

    public ViewIDLocator(@IdRes int id) {
        mID = id;
    }

    @Override
    public @NonNull ViewGroup findAnchorView(@NonNull ViewGroup containerView) {
        return (ViewGroup) containerView.findViewById(mID);
    }
}
