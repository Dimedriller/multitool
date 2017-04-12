package com.dimedriller.presenter;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

public class ViewIDAnchor implements ViewAnchor {
    private final @IdRes int mID;

    public ViewIDAnchor(@IdRes int id) {
        mID = id;
    }

    @Override
    public @NonNull ViewGroup findAnchorView(@NonNull ViewGroup containerView) {
        return (ViewGroup) containerView.findViewById(mID);
    }
}
